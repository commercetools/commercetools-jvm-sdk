package io.sphere.sdk.products;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.sphere.sdk.models.Base;

import javax.annotation.Nullable;

class ProductVariantAvailabilityImpl extends Base implements ProductVariantAvailability {
    @JsonProperty("isOnStock")
    private final Boolean isOnStock;
    @Nullable
    private final Integer restockableInDays;

    @JsonCreator
    ProductVariantAvailabilityImpl(final Boolean isOnStock, @Nullable final Integer restockableInDays) {
        this.isOnStock = isOnStock;
        this.restockableInDays = restockableInDays;
    }

    public Boolean isOnStock() {
        return isOnStock;
    }

    @Nullable
    public Integer getRestockableInDays() {
        return restockableInDays;
    }
}
