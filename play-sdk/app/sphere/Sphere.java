package sphere;

import java.util.Currency;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import io.sphere.client.model.VersionedId;
import io.sphere.client.shop.*;
import io.sphere.client.shop.model.Cart;
import io.sphere.client.shop.model.Customer;
import io.sphere.client.shop.model.CustomerName;
import io.sphere.internal.ChaosMode;
import io.sphere.internal.util.Util;
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

    /** Sphere HTTP API for working with products. */
    public final sphere.ProductService products;
    /** All categories in the project, represented as an in-memory tree.
     *  The category tree is initialized just once on startup. */
    public final CategoryTree categories;
    /** Sphere HTTP API for working with orders. */
    public final sphere.OrderService orders;
    /** Sphere HTTP API for working with customers in a given project.
     *  Use {@link #currentCustomer()} for working with the currently logged in customer. */
    public final sphere.CustomerService customers;
    /** Sphere HTTP API for working with product reviews. */
    public final sphere.ReviewService reviews;
    /** Sphere HTTP API for working with product comments. */
    public final sphere.CommentService comments;
    /** Sphere HTTP API for working with product inventory. */
    public final sphere.InventoryService inventory;

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

    /** Authenticates a customer and stores customer id in the session.
     *
     *  If login was successful, {@link #currentCustomer()} will always return a {@link CurrentCustomer} instance,
     *  until {@link #logout()} is called.
     *
     *  @return True if a customer with given email and password exists, false otherwise. */
    public boolean login(String email, String password) {
        return Async.await(loginAsync(email, password)).isPresent();
    }

    /** Authenticates an existing customer asynchronously and store customer id in the session when finished. */
    public Promise<Optional<AuthenticatedCustomerResult>> loginAsync(String email, String password) {
        if (Strings.isNullOrEmpty(email) || Strings.isNullOrEmpty(password)) {
            throw new IllegalArgumentException("Please provide a non-empty email and password.");
        }
        Log.trace(String.format("[login] Logging in user with email %s.", email));
        Session session = Session.current();
        VersionedId sessionCartId = session.getCartId();
        ListenableFuture<Optional<AuthenticatedCustomerResult>> future;
        if (sessionCartId == null) {
            future = sphereClient.customers().byCredentials(email, password).fetchAsync();
        } else {
            future = sphereClient.carts().loginWithAnonymousCart(sessionCartId, email, password).executeAsync();
        }
        return Async.asPlayPromise(Session.withCustomerAndCartOptional(future, session));
    }

    /** Creates a new customer and authenticates the customer (you don't need  {@link #login}). */
    public Customer signup(String email, String password, CustomerName customerName) {
        return Async.await(signupAsync(email, password, customerName));
    }

    /** Creates a new customer asynchronously and authenticates the customer (calls {@link #login}). */
    public Promise<Customer> signupAsync(String email, String password, CustomerName customerName) {
        Log.trace(String.format("[signup] Signing up user with email %s.", email));
        Session session = Session.current();
        VersionedId sessionCartId = session.getCartId();
        ListenableFuture<Customer> customerFuture;
        if (sessionCartId == null) {
             customerFuture = Session.withCustomerIdAndVersion(
                     sphereClient.customers().signup(
                             email,
                             password,
                             customerName
                     ).executeAsync(),
                     session);
        } else {
            ListenableFuture<AuthenticatedCustomerResult> signupFuture = Session.withCustomerAndCart(
                    sphereClient.customers().signupWithCart(
                            email,
                            password,
                            customerName,
                            sessionCartId.getId(),
                            sessionCartId.getVersion()
                    ).executeAsync(),
                    session);
            customerFuture = Futures.transform(signupFuture, new Function<AuthenticatedCustomerResult, Customer>() {
                public Customer apply(@Nullable AuthenticatedCustomerResult result) {
                    assert result != null;
                    return result.getCustomer();
                }
            });
        }
        return Async.asPlayPromise(customerFuture);
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
