package sphere;

import de.commercetools.sphere.client.shop.model.Cart;
import de.commercetools.sphere.client.shop.model.Customer;
import play.mvc.Http;
import sphere.util.IdWithVersion;
import sphere.util.SessionUtil;

public class Session {
    private final String cartIdKey = "ct-id";
    private final String cartVersionKey = "ct-v";
    private final String cartQuantityKey = "ct-q";
    private final String customerIdKey = "cu-id";
    private final String customerVersionKey = "cu-v";

    private final Http.Session httpSession;

    public Session(Http.Session httpSession) {
        this.httpSession = httpSession;
    }

    // Cart

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

    // Customer
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


    public Http.Session getHttpSession() {
        return httpSession;
    }
}
