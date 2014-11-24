package io.sphere.sdk.products;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.sphere.sdk.models.Base;

import java.util.Optional;

class ProductVariantAvailabilityImpl extends Base implements ProductVariantAvailability {
    @JsonProperty("isOnStock")
    private final boolean isOnStock;
    private final Optional<Integer> restockableInDays;

    ProductVariantAvailabilityImpl(final boolean isOnStock, final Optional<Integer> restockableInDays) {
        this.isOnStock = isOnStock;
        this.restockableInDays = restockableInDays;
    }

    public boolean isOnStock() {
        return isOnStock;
    }

    public Optional<Integer> getRestockableInDays() {
        return restockableInDays;
    }
}
