package io.sphere.sdk.inventory;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.sphere.sdk.models.Base;

import javax.annotation.Nullable;

final class AvailabilityInfoImpl extends Base implements AvailabilityInfo {
    @Nullable
    private final Boolean isOnStock;
    @Nullable
    private final Integer restockableInDays;

    private final Long availableQuantity;

    AvailabilityInfoImpl(@Nullable @JsonProperty("isOnStock") final Boolean isOnStock, final Integer restockableInDays, final Long availableQuantity) {
        this.isOnStock = isOnStock;
        this.restockableInDays = restockableInDays;
        this.availableQuantity = availableQuantity;
    }

    @Override
    public Long getAvailableQuantity() {
        return availableQuantity;
    }

    @Override
    @Nullable
    public Integer getRestockableInDays() {
        return restockableInDays;
    }

    @Nullable
    @Override
    public Boolean isOnStock() {
        return isOnStock;
    }
}
