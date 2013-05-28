package sphere;

/** Exception thrown by
 *  {@link CurrentCart#createOrder(String, io.sphere.client.shop.model.PaymentState) CurrentCart.createOrder}. */
public class CartModifiedException extends RuntimeException {
    public CartModifiedException(String message) {
        super(message);
    }
}