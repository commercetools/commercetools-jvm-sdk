package sphere;

import javax.annotation.Nullable;
import io.sphere.client.SphereResult;
import io.sphere.client.model.VersionedId;
import io.sphere.client.shop.SignInResult;
import io.sphere.client.shop.model.Cart;
import io.sphere.client.shop.model.Customer;
import com.google.common.base.Function;
import com.google.common.base.Optional;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import play.mvc.Http;
import sphere.util.SessionUtil;

/** Helper for storing data in Play session. */
public class Session {
    private static class Keys {
        public static final String cartId = "ctid";
        public static final String cartVersion = "ctv";
        public static final String cartQuantity = "ctq";
        public static final String customerId = "uid";
        public static final String customerVersion = "uv";
    }

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

    static ListenableFuture<SphereResult<SignInResult>> withCustomerAndCart(ListenableFuture<SphereResult<SignInResult>> future, final Session session) {
        return Futures.transform(future, new Function<SphereResult<SignInResult>, SphereResult<SignInResult>>() {
            public SphereResult<SignInResult> apply(@Nullable SphereResult<SignInResult> customerWithCart) {
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

    // temp for [SPHERE-94]
    static ListenableFuture<Optional<SignInResult>> withCustomerAndCartOptional(ListenableFuture<Optional<SignInResult>> future, final Session session) {
        return Futures.transform(future, new Function<Optional<SignInResult>, Optional<SignInResult>>() {
            public Optional<SignInResult> apply(@Nullable Optional<SignInResult> customerWithCart) {
                if (customerWithCart.isPresent()) {
                    Customer customer = customerWithCart.get().getCustomer();
                    Cart cart = customerWithCart.get().getCart();
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

    public @Nullable VersionedId getCartId() {
        return SessionUtil.getIdOrNull(httpSession, Keys.cartId, Keys.cartVersion);
    }

    public Cart putCart(Cart cart) {
        SessionUtil.putIdAndVersion(httpSession, cart.getIdAndVersion(), Keys.cartId, Keys.cartVersion);
        SessionUtil.putInt(httpSession, Keys.cartQuantity, cart.getTotalQuantity());
        return cart;
    }

    public @Nullable Integer getCartTotalQuantity() {
        return SessionUtil.getIntOrNull(httpSession, Keys.cartQuantity);
    }

    public void clearCart() {
        SessionUtil.clearId(httpSession, Keys.cartId, Keys.cartVersion);
        SessionUtil.clear(httpSession, Keys.cartQuantity);
    }

    // ---------------------------------------------
    // Customer
    // ---------------------------------------------

    public @Nullable VersionedId getCustomerId() {
        return SessionUtil.getIdOrNull(httpSession, Keys.customerId, Keys.customerVersion);
    }

    public Customer putCustomer(Customer customer) {
        if (customer == null) return null;
        SessionUtil.putIdAndVersion(httpSession, customer.getIdAndVersion(), Keys.customerId, Keys.customerVersion);
        return customer;
    }

    public void clearCustomer() {
        SessionUtil.clearId(httpSession, Keys.customerId, Keys.customerVersion);
    }
}
