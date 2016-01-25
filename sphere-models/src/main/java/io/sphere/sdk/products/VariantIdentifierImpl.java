package io.sphere.sdk.products;

import com.fasterxml.jackson.annotation.JsonCreator;
import io.sphere.sdk.models.Base;

import javax.annotation.Nullable;

final class VariantIdentifierImpl extends Base implements ByIdVariantIdentifier, BySkuVariantIdentifier {
    @Nullable
    private final String productId;
    @Nullable
    private final Integer variantId;
    @Nullable
    private final String sku;

    @JsonCreator
    VariantIdentifierImpl(@Nullable final String productId, @Nullable final Integer variantId, @Nullable final String sku) {
        this.productId = productId;
        this.variantId = variantId;
        this.sku = sku;
    }

    @Nullable
    @Override
    public String getProductId() {
        return productId;
    }

    @Nullable
    @Override
    public Integer getVariantId() {
        return variantId;
    }

    @Override
    @Nullable
    public String getSku() {
        return sku;
    }
}
