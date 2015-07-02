package io.sphere.sdk.carts.commands.updateactions;

import io.sphere.sdk.carts.Cart;
import io.sphere.sdk.commands.UpdateAction;
import io.sphere.sdk.products.ProductIdentifiable;

/**
 Adds a product variant in the given quantity to the cart.
 If the cart already contains the product variant then only the line item quantity is increased.

 {@include.example io.sphere.sdk.carts.commands.CartUpdateCommandTest#addLineItem()}
 */
public class AddLineItem extends UpdateAction<Cart> {
    private final String productId;
    private final int variantId;
    private final long quantity;

    private AddLineItem(final String productId, final int variantId,
                       final long quantity) {
        super("addLineItem");
        this.productId = productId;
        this.variantId = variantId;
        this.quantity = quantity;
    }

    public static AddLineItem of(final ProductIdentifiable product, final int variantId, final long quantity) {
        return of(product.getId(), variantId, quantity);
    }

    public static AddLineItem of(final String productId, final int variantId, final long quantity) {
        return new AddLineItem(productId, variantId, quantity);
    }

    public String getProductId() {
        return productId;
    }

    public int getVariantId() {
        return variantId;
    }

    public long getQuantity() {
        return quantity;
    }
}
