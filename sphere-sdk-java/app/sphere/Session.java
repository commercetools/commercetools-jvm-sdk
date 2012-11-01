package sphere;

import de.commercetools.sphere.client.shop.model.Cart;
import play.mvc.Http;
import sphere.util.IdWithVersion;
import sphere.util.SessionUtil;

public class Session {
    private final String cartIdKey = "ct-id";
    private final String cartVersionKey = "ct-v";
    private final String cartQuantityKey = "ct-q";

    private final Http.Session httpSession;

    public Session(Http.Session httpSession) {
        this.httpSession = httpSession;
    }

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
}
