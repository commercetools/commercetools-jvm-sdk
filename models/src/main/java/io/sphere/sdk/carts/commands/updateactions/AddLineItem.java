package io.sphere.sdk.carts.commands.updateactions;

import io.sphere.sdk.carts.Cart;
import io.sphere.sdk.commands.UpdateAction;

/**
 Adds a product variant in the given quantity to the cart.
 If the cart already contains the product variant then only the line item quantity is increased.

 {@include.example io.sphere.sdk.carts.commands.CartUpdateCommandTest#addLineItem()}
 */
public class AddLineItem extends UpdateAction<Cart> {
    private final String productId;
    private final int variantId;
    private final int quantity;

    private AddLineItem(final String productId, final int variantId,
                       final int quantity) {
        super("addLineItem");
        this.productId = productId;
        this.variantId = variantId;
        this.quantity = quantity;
    }

    public static AddLineItem of(final String productId, final int variantId,
                              final int quantity) {
        return new AddLineItem(productId, variantId, quantity);
    }

    public String getProductId() {
        return productId;
    }

    public int getVariantId() {
        return variantId;
    }

    public int getQuantity() {
        return quantity;
    }
}
