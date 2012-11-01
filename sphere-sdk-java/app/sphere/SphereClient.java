package sphere;

import com.google.common.base.Function;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import de.commercetools.internal.util.Log;
import de.commercetools.sphere.client.CommandRequest;
import de.commercetools.sphere.client.QueryRequest;
import de.commercetools.sphere.client.SphereException;
import de.commercetools.sphere.client.shop.*;
import de.commercetools.sphere.client.shop.model.Customer;
import net.jcip.annotations.ThreadSafe;
import play.mvc.Http;

import javax.annotation.Nullable;
import java.util.Currency;

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

    SphereClient(Config config, ShopClient shopClient) {
        this.underlyingClient = shopClient;
        shopCurrency = config.shopCurrency();
        products = underlyingClient.products();
        categories = underlyingClient.categories();
        orders = underlyingClient.orders();
    }

    /** API for working with cart bound to the current request. */
    public CurrentCart currentCart() {
        return new CurrentCart(
                new Session(Http.Context.current().session()),
                this.underlyingClient.carts(),
                shopCurrency);
    }

    /** API for working with the customer bound to the current request.
     *
     * @return The current customer if the customer id exists in the session, otherwise null.
     */
    public CurrentCustomer currentCustomer() {
       return CurrentCustomer.getCurrentCustomer(Http.Context.current().session(), this.underlyingClient.customers());
    }

    public Customer login(String email, String password) {
        try {
            return loginAsync(email, password).get();
        } catch(Exception e) {
            throw new SphereException(e);
        }
    }

    public ListenableFuture<Customer> loginAsync(String email, String password) {
        Log.trace(String.format("[login] Loggin in user with email %s.", email)); //TODO is logging email ok?
        final QueryRequest<Customer> qr = this.underlyingClient.customers().login(email, password);
        return Futures.transform(qr.fetchAsync(), new Function<Customer, Customer>() {
            @Override
            public Customer apply(@Nullable Customer customer) {
                new Session(Http.Context.current().session()).putCustomer(customer);
                return customer;
            }
        });
    }

    public Customer signup(String email,
                           String password,
                           String firstName,
                           String lastName,
                           String middleName,
                           String title) {
        try {
            return signupAsync(email, password, firstName, lastName, middleName, title).get();
        } catch(Exception e) {
            throw new SphereException(e);
        }
    }

    public ListenableFuture<Customer> signupAsync(String email,
                                                  String password,
                                                  String firstName,
                                                  String lastName,
                                                  String middleName,
                                                  String title) {
        Log.trace(String.format("[signup] Signing up user with email %s.", email)); //TODO is logging email ok?
        final CommandRequest<Customer> qr = this.underlyingClient.customers().signup(email, password, firstName, lastName, middleName, title);
        return Futures.transform(qr.executeAsync(), new Function<Customer, Customer>() {
            @Override
            public Customer apply(@Nullable Customer customer) {
                new Session(Http.Context.current().session()).putCustomer(customer);
                return customer;
            }
        });
    }


}
