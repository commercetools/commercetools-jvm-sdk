package sphere;

import java.util.Currency;
import java.util.concurrent.ExecutionException;

import de.commercetools.internal.util.Log;
import de.commercetools.sphere.client.SphereException;
import de.commercetools.sphere.client.shop.*;
import de.commercetools.sphere.client.shop.model.Customer;
import sphere.util.IdWithVersion;

import play.mvc.Http;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.base.Function;
import com.google.common.util.concurrent.Futures;
import net.jcip.annotations.ThreadSafe;

import javax.annotation.Nullable;

/** Client for accessing all Sphere APIs for building a store.
 *  To obtain an instance of this class designed to be shared by all the controllers in your application,
 *  use the static method {@link Sphere#getClient}. */
@ThreadSafe
public class SphereClient {
    private final ShopClient underlyingClient;
    private final Currency shopCurrency;

    /** API for fetching and searching Products. */
    public final Products products;

    /** API for fetching categories. */
    public final CategoryTree categories;

    /** API for fetching orders. */
    public final Orders orders;

    /** API for customer operations that don't require customer id/version.
     *  Use currentCustomer() for the id/version related operations. */
    public final BasicCustomerService customers;

    SphereClient(Config config, ShopClient shopClient) {
        this.underlyingClient = shopClient;
        shopCurrency = config.shopCurrency();
        products = underlyingClient.products();
        categories = underlyingClient.categories();
        orders = underlyingClient.orders();
        customers = underlyingClient.customers();
    }

    /** API for working with cart bound to the current request. */
    public CurrentCart currentCart() {
        return new CurrentCart(this.underlyingClient.carts(), shopCurrency);
    }

    /** API for working with the customer bound to the current request.
     *
     * @return The current customer if the customer id with version exists in the http session, otherwise null.
     */
    public CurrentCustomer currentCustomer() {
       return CurrentCustomer.getCurrentCustomer(this.underlyingClient.customers(), this.underlyingClient.orders());
    }

    /** Authenticate an existing customer and store customer id in the session.
     *  If this methods returns true, {@link #currentCustomer()} will keep returning a {@link CurrentCustomer} instance
     *  until a {@link #logout()} is called. */
    public boolean login(String email, String password) {
        try {
            return loginAsync(email, password).get() != null;
        } catch (InterruptedException e) {
            throw new SphereException(e);
        } catch (ExecutionException e) {
            throw new SphereException(e);
        }
    }

    /** Authenticates an existing customer asynchronously and store customer id in the session when finished. */
    public ListenableFuture<LoginResult> loginAsync(String email, String password) {
        Log.trace(String.format("[login] Logging in user with email %s.", email)); // TODO is logging email ok?
        Session session = Session.current();
        IdWithVersion sessionCartId = session.getCartId();
        ListenableFuture<LoginResult> future;
        if (sessionCartId == null)
            future = this.underlyingClient.customers().login(email, password).fetchAsync();
        else
            future = this.underlyingClient.carts().loginWithAnonymousCart(sessionCartId.id(), sessionCartId.version(), email, password).executeAsync();

        return Session.withLoginResultIds(future, session);
    }

    /** Creates a new customer in the backend and returns it.
     *  If this method succeeds, it's possible to immediately use {@link #login}. */
    public Customer signup(String email, String password, String firstName, String lastName, String middleName, String title) {
        try {
            return signupAsync(email, password, firstName, lastName, middleName, title).get();
        } catch(Exception e) {
            throw new SphereException(e);
        }
    }

    /** Creates a new customer in the backend and returns it asynchronously.
     *  After the returned future succeeds, it's possible to immediately use {@link #login}. */
    public ListenableFuture<Customer> signupAsync(String email, String password, String firstName, String lastName, String middleName, String title) {
        Log.trace(String.format("[signup] Signing up user with email %s.", email)); // TODO is logging email ok?
        Session session = Session.current();
        IdWithVersion sessionCartId = session.getCartId();
        if (sessionCartId == null) {
            return Session.withCustomerId(
                this.underlyingClient.customers().signup(email, password, firstName, lastName, middleName, title).executeAsync(),
                session);
        } else {
            ListenableFuture<LoginResult> loginResult = Session.withLoginResultIds(
                    this.underlyingClient.customers().signupWithCart(email,password, firstName, lastName, middleName,
                            title, sessionCartId.id(), sessionCartId.version()).executeAsync(),
                    session);
            return  Futures.transform(loginResult, new Function<LoginResult, Customer>() {
                @Override
                public Customer apply(@Nullable LoginResult result) {
                    return result.getCustomer();
                }
            });
        }
    }

    /** Removes the customer and cart information from the session.
     *
     *  After logout, {@link #currentCustomer()} will return null.
     *  Don't keep the old {@link CurrentCustomer} instance around - it will throw {@link IllegalStateException}s
     *  if used after logout. */
    public void logout() {
        Session session = Session.current();
        session.clearCustomer();
        session.clearCart();
    }

}
