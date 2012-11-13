package sphere;

import de.commercetools.sphere.client.shop.model.Cart;
import de.commercetools.sphere.client.shop.model.Customer;
import sphere.util.IdWithVersion;
import sphere.util.SessionUtil;

import play.mvc.Http;
import com.google.common.base.Function;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import de.commercetools.sphere.client.shop.LoginResult;

import javax.annotation.Nullable;

/** Helper for storing data in Play session. */
public class Session {
    private final String cartIdKey = "ctid";
    private final String cartVersionKey = "ctv";
    private final String cartQuantityKey = "ctq";
    private final String customerIdKey = "uid";
    private final String customerVersionKey = "uv";

    private final Http.Session httpSession;

    public Session(Http.Session httpSession) {
        this.httpSession = httpSession;
    }

    public Http.Session getHttpSession() {
        return httpSession;
    }

    public static Session current() {
        return new Session(Http.Context.current().session());
    }

    static ListenableFuture<Customer> withCustomerId(ListenableFuture<Customer> future, final Session session) {
        return Futures.transform(future, new Function<Customer, Customer>() {
            @Override
            public Customer apply(@Nullable Customer customer) {
                session.putCustomer(customer);
                return customer;
            }
        });
    }

    static ListenableFuture<LoginResult> withLoginResultIds(ListenableFuture<LoginResult> future, final Session session) {
        return Futures.transform(future, new Function<LoginResult, LoginResult>() {
            @Override public LoginResult apply(@Nullable LoginResult result) {
                session.putCustomer(result.getCustomer());
                if (result.getCart() != null) { session.putCart(result.getCart()); }
                return result;
            }
        });
    }

    // ---------------------------------------------
    // Cart
    // ---------------------------------------------

    public static IdWithVersion createCartId(Cart cart) {
        return new IdWithVersion(cart.getId(), cart.getVersion());
    }

    public IdWithVersion getCartId() {
        return SessionUtil.getIdOrNull(httpSession, cartIdKey, cartVersionKey);
    }

    public void putCart(Cart cart) {
        SessionUtil.putId(httpSession, createCartId(cart), cartIdKey, cartVersionKey);
        SessionUtil.putInt(httpSession, cartQuantityKey, cart.getTotalQuantity());
    }

    public Integer getCartTotalQuantity() {
        return SessionUtil.getIntOrNull(httpSession, cartQuantityKey);
    }

    public void clearCart() {
        SessionUtil.clearId(httpSession, cartIdKey, cartVersionKey);
        SessionUtil.clear(httpSession, cartQuantityKey);
    }

    // ---------------------------------------------
    // Customer
    // ---------------------------------------------

    public static IdWithVersion createCustomerId(Customer customer) {    //TODO make an versionable inteface on customer, cart, ...
        return new IdWithVersion(customer.getId(), customer.getVersion());
    }

    public IdWithVersion getCustomerId() {
        return SessionUtil.getIdOrNull(httpSession, customerIdKey, customerVersionKey);
    }

    public void putCustomer(Customer customer) {
        if (customer == null)
            return;
        SessionUtil.putId(httpSession, createCustomerId(customer), customerIdKey, customerVersionKey);
    }

    public void clearCustomer() {
        SessionUtil.clearId(httpSession, customerIdKey, customerVersionKey);
    }
}
