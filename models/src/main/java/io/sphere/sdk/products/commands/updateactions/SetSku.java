package io.sphere.sdk.products.commands.updateactions;

import io.sphere.sdk.commands.UpdateAction;
import io.sphere.sdk.products.Product;

import java.util.Optional;

public class SetSku extends UpdateAction<Product> {
    private final int variantId;
    private final Optional<String> sku;

    private SetSku(final int variantId, final Optional<String> sku) {
        super("setSKU");
        this.variantId = variantId;
        this.sku = sku;
    }

    public long getVariantId() {
        return variantId;
    }

    public Optional<String> getSku() {
        return sku;
    }

    public static SetSku of(final int variantId, final Optional<String> sku) {
        return new SetSku(variantId, sku);
    }

    public static SetSku of(final int variantId, final String sku) {
        return of(variantId, Optional.of(sku));
    }
}
