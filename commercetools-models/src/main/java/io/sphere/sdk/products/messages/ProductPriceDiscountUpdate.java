package io.sphere.sdk.products.messages;

import com.fasterxml.jackson.annotation.JsonCreator;
import io.sphere.sdk.models.Base;
import io.sphere.sdk.productdiscounts.DiscountedPrice;

import javax.annotation.Nullable;

public final class ProductPriceDiscountUpdate extends Base {
    private final Long variantId;

    @Nullable
    private final String variantKey;

    @Nullable
    private final String sku;

    private final String priceId;

    @Nullable
    private final DiscountedPrice discounted;

    private final Boolean staged;


    @JsonCreator
    ProductPriceDiscountUpdate(Long variantId, @Nullable String variantKey, @Nullable String sku, String priceId, @Nullable DiscountedPrice discounted, Boolean staged) {
        this.variantId = variantId;
        this.variantKey = variantKey;
        this.sku = sku;
        this.priceId = priceId;
        this.discounted = discounted;
        this.staged = staged;
    }



    public Long getVariantId() {
        return variantId;
    }

    @Nullable
    public String getVariantKey() {
        return variantKey;
    }

    @Nullable
    public String getSku() {
        return sku;
    }

    public String getPriceId() {
        return priceId;
    }

    @Nullable
    public DiscountedPrice getDiscounted() {
        return discounted;
    }

    public Boolean getStaged() {
        return staged;
    }

}
