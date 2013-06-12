package sphere;

import java.util.Currency;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import io.sphere.client.SphereResult;
import io.sphere.client.model.VersionedId;
import io.sphere.client.shop.CategoryTree;
import io.sphere.client.shop.SignInResult;
import io.sphere.client.shop.SphereClient;
import io.sphere.client.shop.model.Cart;
import io.sphere.client.shop.model.Customer;
import io.sphere.client.shop.model.CustomerName;
import io.sphere.internal.ChaosMode;
import io.sphere.internal.util.Util;
import io.sphere.client.exceptions.*;
import net.jcip.annotations.GuardedBy;
import play.libs.F.Promise;
import sphere.internal.*;
import io.sphere.internal.util.Log;
import com.google.common.base.Function;
import com.google.common.base.Optional;
import com.google.common.base.Strings;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import net.jcip.annotations.ThreadSafe;
import sphere.util.Async;

/** Client for accessing all Sphere APIs.
 *
 *  <p>To obtain a thread-safe singleton instance of this class that can be used from all controllers
 *  in your application, use the static method {@link #getInstance() getInstance}. */
@ThreadSafe
public class Sphere {
    private final SphereClient sphereClient;
    private final Currency cartCurrency;
    private final Cart.InventoryMode cartInventoryMode;

    private final sphere.ProductService products;
    private final CategoryTree categories;
    private final sphere.OrderService orders;
    private final sphere.CustomerService customers;
    private final sphere.ReviewService reviews;
    private final sphere.CommentService comments;
    private final sphere.InventoryService inventory;
    private final ShippingMethodService shippingMethods;
    
    /** Sphere HTTP API for working with products. */
    public sphere.ProductService products() { return products; }
    /** All categories in the project, represented as an in-memory tree.
     *  The category tree is initialized just once on startup. */
    public CategoryTree categories() { return categories; }
    /** Sphere HTTP API for working with orders. */
    public sphere.OrderService orders() { return orders; }
    /** Sphere HTTP API for working with customers in a given project.
     *  Use {@link #currentCustomer()} for working with the currently logged in customer. */
    public sphere.CustomerService customers() { return customers; }
    /** Sphere HTTP API for working with product reviews. */
    public sphere.ReviewService reviews() { return reviews; }
    /** Sphere HTTP API for working with product comments. */
    public sphere.CommentService comments() { return comments; }
    /** Sphere HTTP API for working with product inventory. */
    public sphere.InventoryService inventory() { return inventory; }
    /** Sphere HTTP API for working with shipping methods. */
    public ShippingMethodService shippingMethods() { return shippingMethods; }
    
    Sphere(Config sphereConfig, SphereClient sphereClient) {
        cartCurrency = sphereConfig.cartCurrency();
        cartInventoryMode = sphereConfig.cartInventoryMode();
        this.sphereClient = sphereClient;
        categories = this.sphereClient.categories();
        products = new ProductServiceAdapter(this.sphereClient.products());
        orders = new OrderServiceAdapter(this.sphereClient.orders());
        customers = new CustomerServiceAdapter(this.sphereClient.customers());
        comments = new CommentServiceAdapter(this.sphereClient.comments());
        reviews = new ReviewServiceAdapter(this.sphereClient.reviews());
        inventory = new InventoryServiceAdapter(this.sphereClient.inventory());
        shippingMethods = new ShippingMethodServiceAdapter(this.sphereClient.shippingMethods());
    }

    /** Provides access to the low-level Sphere Java client, may you need it to perform tasks that
     *  are not common in a typical online shop scenario.
     *
     * <p>Using the shop client, you can access all Sphere HTTP API endpoints, so you can for example
     * create shopping carts freely, fetch orders for any customer, etc. */
    public SphereClient client() {
        return this.sphereClient;
    }

    /** Returns a Cart API object for the current request.
     * This object is automatically associated to the current HTTP session, meaning the
     * cart is preserved for a user.
     *
     *  @return A cart API object. This method never returns null. */
    @Nonnull public CurrentCart currentCart() {
        return new CurrentCart(sphereClient.carts(), sphereClient.orders(), cartCurrency, cartInventoryMode);
    }

    // ---------------------------
    // Singleton
    // ---------------------------

    private static Object instanceLock = new Object();
    @GuardedBy("clientLock")
    private static volatile Sphere instance;

    /** Returns a thread-safe client for accessing the Sphere APIs.
     *  The returned instance is designed to be shared by all controllers in your application. */
    public static Sphere getInstance() {
        Sphere result = instance;
        if (result == null) {
            synchronized (instanceLock) {
                result = instance;
                if (result == null) {
                    instance = result = create();
                    initChaosLevel();
                }
            }
        }
        return result;
    }

    /** Top-level DI entry point where everything gets wired together. */
    private static Sphere create() {
        try {
            SphereConfig config = SphereConfig.root();
            return new Sphere(SphereConfig.root(), SphereClient.create(config.createSphereClientConfig()));
        } catch (Exception e) {
            throw Util.toSphereException(e);
        }
    }

    // ---------------------------
    // Chaos mode
    // ---------------------------

    /** Initializes the static chaos level. The chaos level is a static variable so we don't have to pass it around
     *  everywhere, and it's the only exception where we do this. */
    private static void initChaosLevel() {
        ChaosMode.setChaosLevel(SphereConfig.root().chaosLevel());
    }

    // ---------------------------
    // Business logic
    // ---------------------------

    /** Returns true if a customer is currently logged in, false otherwise.
     * This is equivalent to {@code currentCustomer() != null} but more readable. */
    public boolean isLoggedIn() {
        return currentCustomer() != null;
    }

    /** Customer object for the current session.
     *  @return The current customer if a customer is logged in, null otherwise. */
    public CurrentCustomer currentCustomer() {
       return CurrentCustomer.createFromSession(
               sphereClient.customers(), sphereClient.orders(),
               sphereClient.comments(), sphereClient.reviews());
    }

    // This could return:
    //   SphereResult<SignInResult> => shop code: if (sphere().login(email, pass).isSuccess()), nothing else
    /** Authenticates a customer and stores customer id in the session.
     *
     *  If login was successful, {@link #currentCustomer()} will always return a {@link CurrentCustomer} instance,
     *  until {@link #logout()} is called.
     *
     *  @return True if the customer was successfully authenticated, false otherwise. */
    public boolean login(String email, String password) {
        return Async.await(loginAsync(email, password)).isSuccess();
    }

    /** Authenticates an existing customer asynchronously and store customer id in the session when finished. */
    public Promise<SphereResult<SignInResult>> loginAsync(String email, String password) {
        if (Strings.isNullOrEmpty(email) || Strings.isNullOrEmpty(password)) {
            throw new IllegalArgumentException("Please provide a non-empty email and password.");
        }
        Log.trace(String.format("[login] Logging in user with email %s.", email));
        Session session = Session.current();
        VersionedId sessionCartId = session.getCartId();
        ListenableFuture<SphereResult<SignInResult>> loginFuture = sessionCartId == null ?
                sphereClient.customers().signIn(email, password).executeAsync() :
                sphereClient.customers().signIn(email, password, sessionCartId.getId()).executeAsync();
        return Async.asPlayPromise(Session.withCustomerAndCart(loginFuture, session));
    }

    /** Creates and authenticates a new customer
     *  (you don't have to to call {@link #login(String, String) login} explicitly).
     *
     *  @throws EmailAlreadyInUseException if the email is already taken. */
    public SignInResult signup(String email, String password, CustomerName customerName) {
        return Async.awaitResult(signupAsync(email, password, customerName));
    }

    /** Creates and authenticates a new customer asynchronously
     *  (you don't have to to call {@link #login(String, String) login} explicitly).
     *
     *  @return A result which can fail with the following exceptions:
     *  <ul>
     *      <li>{@link EmailAlreadyInUseException} if the email is already taken.
     *  </>*/
    public Promise<SphereResult<SignInResult>> signupAsync(String email, String password, CustomerName customerName) {
        Log.trace(String.format("[signup] Signing up user with email %s.", email));
        Session session = Session.current();
        VersionedId sessionCartId = session.getCartId();
        ListenableFuture<SphereResult<SignInResult>> signupFuture = sessionCartId == null ?
                sphereClient.customers().signUp(email, password, customerName).executeAsync() :
                sphereClient.customers().signUp(email, password, customerName, sessionCartId.getId()).executeAsync();
        return Async.asPlayPromise(Session.withCustomerAndCart(signupFuture, session));
    }

    /** Removes the customer and cart information from the session.
     *
     *  <p>After calling logout, {@link #currentCustomer()} will return null.
     *  Make sure you don't keep old {@link CurrentCustomer} instances around as they become invalid after logout. */
    public void logout() {
        Session session = Session.current();
        session.clearCustomer();
        session.clearCart();
    }
}
