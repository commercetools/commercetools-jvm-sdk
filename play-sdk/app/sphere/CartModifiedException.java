package sphere;

/** Exception thrown by
 *  {@link CurrentCart#createOrder(String, io.sphere.client.shop.model.PaymentState) CurrentCart.createOrder}
 *  when a cart id doesn't match a checkout snapshot id. */
public class CartModifiedException extends RuntimeException {
    public CartModifiedException(String message) {
        super(message);
    }
}