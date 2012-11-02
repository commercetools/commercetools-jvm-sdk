package sphere;

import de.commercetools.sphere.client.shop.model.Cart;
import de.commercetools.sphere.client.shop.model.Customer;
import sphere.util.IdWithVersion;
import sphere.util.SessionUtil;
import play.mvc.Http;

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
        SessionUtil.putId(httpSession, createCustomerId(customer), customerIdKey, customerVersionKey);
    }

    public void clearCustomer() {
        SessionUtil.clearId(httpSession, customerIdKey, customerVersionKey);
    }
}
