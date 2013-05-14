package sphere;

import com.google.common.base.Optional;
import io.sphere.client.model.VersionedId;
import io.sphere.client.shop.AuthenticatedCustomerResult;
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

    static ListenableFuture<Customer> withCustomerIdAndVersion(ListenableFuture<Customer> future, final Session session) {
        return Futures.transform(future, new Function<Customer, Customer>() {
            public Customer apply(@Nullable Customer customer) {
                session.putCustomerIdAndVersion(customer);
                return customer;
            }
        });
    }

    static ListenableFuture<Optional<Customer>> withCustomerIdAndVersionOptional(ListenableFuture<Optional<Customer>> future, final Session session) {
        return Futures.transform(future, new Function<Optional<Customer>, Optional<Customer>>() {
            public Optional<Customer> apply(@Nullable Optional<Customer> optionalCustomer) {
                if (optionalCustomer.isPresent()) {
                    session.putCustomerIdAndVersion(optionalCustomer.get());
                }
                return optionalCustomer;
            }
        });
    }

    static ListenableFuture<Optional<AuthenticatedCustomerResult>> withCustomerAndCartOptional(ListenableFuture<Optional<AuthenticatedCustomerResult>> future, final Session session) {
        return Futures.transform(future, new Function<Optional<AuthenticatedCustomerResult>, Optional<AuthenticatedCustomerResult>>() {
            public Optional<AuthenticatedCustomerResult> apply(@Nullable Optional<AuthenticatedCustomerResult> result) {
                if (result == null) {
                    return Optional.absent();
                }
                if (result.isPresent()) {
                    session.putCustomerIdAndVersion(result.get().getCustomer());
                    if (result.get().getCart() != null) { session.putCart(result.get().getCart()); }
                }
                return result;
            }
        });
    }

    static ListenableFuture<AuthenticatedCustomerResult> withCustomerAndCart(ListenableFuture<AuthenticatedCustomerResult> future, final Session session) {
        return Futures.transform(future, new Function<AuthenticatedCustomerResult, AuthenticatedCustomerResult>() {
            public AuthenticatedCustomerResult apply(@Nullable AuthenticatedCustomerResult result) {
                session.putCustomerIdAndVersion(result.getCustomer());
                if (result.getCart() != null) { session.putCart(result.getCart()); }
                return result;
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

    public void putCustomerIdAndVersion(Customer customer) {
        if (customer == null) return;
        SessionUtil.putIdAndVersion(httpSession, customer.getIdAndVersion(), customerIdKey, customerVersionKey);
    }

    public void clearCustomer() {
        SessionUtil.clearId(httpSession, customerIdKey, customerVersionKey);
    }
}
