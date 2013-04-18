package sphere;

import java.util.Currency;
import javax.annotation.Nullable;
import io.sphere.client.shop.*;
import io.sphere.client.shop.model.Cart;
import io.sphere.client.shop.model.Customer;
import io.sphere.client.shop.model.CustomerName;
import io.sphere.internal.util.Log;
import io.sphere.internal.util.Util;
import com.google.common.base.Function;
import com.google.common.base.Optional;
import com.google.common.base.Strings;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import net.jcip.annotations.ThreadSafe;
import play.mvc.Result;
import play.mvc.Results;
import sphere.util.Async;
import sphere.util.IdWithVersion;

/** Client for accessing all Sphere APIs.
 *
 *  To obtain an instance of this class designed to be shared by all the controllers in your application,
 *  use the static method {@link Sphere#getClient}. */
@ThreadSafe
public class SphereClient {
    private final ShopClient shopClient;
    private final Currency cartCurrency;
    private final Cart.InventoryMode cartInventoryMode;

    /** API for fetching and searching Products. */
    public final ProductService products;
    /** API for fetching categories. */
    public final CategoryTree categories;
    /** API for working with orders. */
    public final OrderService orders;
    /** API for customer operations that don't require customer id/version.
     *  Use currentCustomer() for the id/version related operations. */
    public final BasicCustomerService customers;
    /** API for product reviews. */
    public final ReviewService reviews;
    /** API for product comments. */
    public final CommentService comments;
    /** API for product inventory. */
    public final InventoryService inventory;

    SphereClient(Config sphereConfig, ShopClient shopClient) {
        cartCurrency = sphereConfig.cartCurrency();
        cartInventoryMode = sphereConfig.cartInventoryMode();
        this.shopClient = shopClient;
        products = this.shopClient.products();
        categories = this.shopClient.categories();
        orders = this.shopClient.orders();
        customers = this.shopClient.customers();
        comments = this.shopClient.comments();
        reviews = this.shopClient.reviews();
        inventory = this.shopClient.inventory();
    }

    /** Analogy of Play's {@link Results#async(play.libs.F.Promise)} that works with
     * Guava futures used by the Sphere client.
     *
     * <p>Example:
     * <pre>{@code
     * public class ShoppingCart extends ShopController {
     *   private static Form<AddToCartValues> addToCartForm = form(AddToCartValues.class);
     *
     *   public static Result show() {
     *       return sphere().async(sphere().currentCart().fetchAsync());
     *   }
     *
     *   public static Result addItem() {
     *       AddToCartValues values = addToCartForm.bindFromRequest().get();
     *       ListenableFuture<Cart> addItem = Futures.immediateFuture(null);
     *       if (!Strings.isNullOrEmpty(values.productId) && values.variantId != null) {
     *           addItem = sphere().currentCart().addLineItemAsync(values.productId, values.variantId, 1);
     *       }
     *       return sphere().async(Futures.transform(addItem, new Function<Cart, Result>() {
     *           @Override public Result apply(@Nullable Cart cart) {
     *               return redirect(controllers.routes.ShoppingCart.show());
     *           }
     *       }));
     *   }
     * }
     * }</pre>
     * */
    // This is an instance method only to be easily discoverable in code completion.
    public static Results.AsyncResult async(ListenableFuture<Result> resultFuture) {
        return Async.asyncResult(resultFuture);
    }

    /** Cart object for the current session.
     *
     *  @return A cart object if a customer is logged in. Dummy cart object with default values otherwise.*/
    public CurrentCart currentCart() {
        return new CurrentCart(shopClient.carts(), shopClient.orders(), cartCurrency, cartInventoryMode);
    }

    /** Returns true if a customer is currently logged in, false otherwise.
     *
     * This is equivalent to checking for {@code currentCustomer() != null} but more readable. */
    public boolean isLoggedIn() {
        return currentCustomer() != null;
    }

    /** Customer object for the current session.
     *
     *  @return The current customer if a customer is logged in, null otherwise. */
    public CurrentCustomer currentCustomer() {
       return CurrentCustomer.createFromSession(
               shopClient.customers(), shopClient.orders(),
               shopClient.comments(), shopClient.reviews());
    }

    /** Authenticates a customer and stores customer id in the session.
     *
     *  If login was successful, {@link #currentCustomer()} will always return a {@link CurrentCustomer} instance
     *  until {@link #logout()} is called.
     *
     *  @return true if a customer with given email and password exists, false otherwise. */
    public boolean login(String email, String password) {
        return Util.sync(loginAsync(email, password)).isPresent();
    }

    /** Authenticates an existing customer asynchronously and store customer id in the session when finished. */
    public ListenableFuture<Optional<AuthenticatedCustomerResult>> loginAsync(String email, String password) {
        if (Strings.isNullOrEmpty(email) || Strings.isNullOrEmpty(password)) {
            throw new IllegalArgumentException("Please provide a non-empty email and password.");
        }
        Log.trace(String.format("[login] Logging in user with email %s.", email));
        Session session = Session.current();
        IdWithVersion sessionCartId = session.getCartId();
        ListenableFuture<Optional<AuthenticatedCustomerResult>> future;
        if (sessionCartId == null) {
            future = shopClient.customers().byCredentials(email, password).fetchAsync();
        } else {
            future = shopClient.carts().loginWithAnonymousCart(
                    sessionCartId.getId(), sessionCartId.getVersion(), email, password).executeAsync();
        }
        return Session.withCustomerAndCartOptional(future, session);
    }

    /** Creates a new customer and authenticates the customer (you don't need  {@link #login}). */
    public Customer signup(String email, String password, CustomerName customerName) {
        return Util.sync(signupAsync(email, password, customerName));
    }

    /** Creates a new customer asynchronously and authenticates the customer (calls {@link #login}). */
    public ListenableFuture<Customer> signupAsync(String email, String password, CustomerName customerName) {
        Log.trace(String.format("[signup] Signing up user with email %s.", email));
        Session session = Session.current();
        IdWithVersion sessionCartId = session.getCartId();
        ListenableFuture<Customer> customerFuture;
        if (sessionCartId == null) {
             customerFuture = Session.withCustomerIdAndVersion(
                     shopClient.customers().signup(
                             email,
                             password,
                             customerName.getFirstName(),
                             customerName.getLastName(),
                             customerName.getMiddleName(),
                             customerName.getTitle()
                     ).executeAsync(),
                     session);
        } else {
            ListenableFuture<AuthenticatedCustomerResult> signupFuture = Session.withCustomerAndCart(
                    shopClient.customers().signupWithCart(
                            email,
                            password,
                            customerName.getFirstName(),
                            customerName.getLastName(),
                            customerName.getMiddleName(),
                            customerName.getTitle(),
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
        return customerFuture;
    }

    /** Removes the customer and cart information from the session.
     *
     *  After logout, {@link #currentCustomer()} will return null.
     *  Make sure you don't keep old {@link CurrentCustomer} instances around because they become invalid after logout. */
    public void logout() {
        Session session = Session.current();
        session.clearCustomer();
        session.clearCart();
    }
}
