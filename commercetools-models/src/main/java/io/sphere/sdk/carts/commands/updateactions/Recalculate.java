package io.sphere.sdk.carts.commands.updateactions;

import io.sphere.sdk.carts.Cart;
import io.sphere.sdk.commands.UpdateActionImpl;

import javax.annotation.Nullable;

/**
 Updates tax rates and prices.

 {@doc.gen intro}

 {@include.example io.sphere.sdk.carts.commands.CartUpdateCommandIntegrationTest#recalculate()}
 */
public final class Recalculate extends UpdateActionImpl<Cart> {
    @Nullable
    private final Boolean updateProductData;

    private Recalculate(@Nullable final Boolean updateProductData) {
        super("recalculate");
        this.updateProductData = updateProductData;
    }

    public static Recalculate of(final Boolean updateProductData) {
        return new Recalculate(updateProductData);
    }

    public static Recalculate of() {
        return new Recalculate(null);
    }

    @Nullable
    public Boolean isUpdateProductData() {
        return updateProductData;
    }
}
