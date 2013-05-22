package sphere;

import io.sphere.client.SphereResult;
import io.sphere.client.model.VersionedId;
import io.sphere.client.shop.CustomerWithCart;
import io.sphere.client.shop.model.Cart;
import io.sphere.client.shop.model.Customer;
import sphere.util.SessionUtil;

import play.mvc.Http;
import com.google.common.base.Function;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;

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

    static ListenableFuture<SphereResult<Customer>> withCustomerIdAndVersion(ListenableFuture<SphereResult<Customer>> future, final Session session) {
        return Futures.transform(future, new Function<SphereResult<Customer>, SphereResult<Customer>>() {
            public SphereResult<Customer> apply(SphereResult<Customer> customer) {
                if (customer.isSuccess()) {
                    session.putCustomer(customer.getValue());
                }
                return customer;
            }
        });
    }

    static ListenableFuture<SphereResult<CustomerWithCart>> withCustomerAndCart(ListenableFuture<SphereResult<CustomerWithCart>> future, final Session session) {
        return Futures.transform(future, new Function<SphereResult<CustomerWithCart>, SphereResult<CustomerWithCart>>() {
            public SphereResult<CustomerWithCart> apply(@Nullable SphereResult<CustomerWithCart> customerWithCart) {
                if (customerWithCart.isSuccess()) {
                    Customer customer = customerWithCart.getValue().getCustomer();
                    Cart cart = customerWithCart.getValue().getCart();
                    session.putCustomer(customer);
                    if (cart != null) { session.putCart(cart); }
                }
                return customerWithCart;
            }
        });
    }

    // ---------------------------------------------
    // Cart
    // ---------------------------------------------

    public VersionedId getCartId() {
        return SessionUtil.getIdOrNull(httpSession, cartIdKey, cartVersionKey);
    }

    public void putCart(Cart cart) {
        SessionUtil.putIdAndVersion(httpSession, cart.getIdAndVersion(), cartIdKey, cartVersionKey);
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

    public VersionedId getCustomerId() {
        return SessionUtil.getIdOrNull(httpSession, customerIdKey, customerVersionKey);
    }

    public void putCustomer(Customer customer) {
        if (customer == null) return;
        SessionUtil.putIdAndVersion(httpSession, customer.getIdAndVersion(), customerIdKey, customerVersionKey);
    }

    public void clearCustomer() {
        SessionUtil.clearId(httpSession, customerIdKey, customerVersionKey);
    }
}
