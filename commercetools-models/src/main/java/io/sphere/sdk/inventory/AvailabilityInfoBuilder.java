package io.sphere.sdk.inventory;

import io.sphere.sdk.models.Base;
import io.sphere.sdk.models.Builder;

import javax.annotation.Nullable;

public final class AvailabilityInfoBuilder extends Base implements Builder<AvailabilityInfo> {
    @Nullable
    private Boolean isOnStock;
    @Nullable
    private Integer restockableInDays;
    @Nullable
    private Long availableQuantity;

    private AvailabilityInfoBuilder() {
    }

    public static AvailabilityInfoBuilder of() {
        return new AvailabilityInfoBuilder();
    }

    public AvailabilityInfoBuilder isOnStock(@Nullable final Boolean isOnStock) {
        this.isOnStock = isOnStock;
        return this;
    }

    public AvailabilityInfoBuilder restockableInDays(@Nullable final Integer restockableInDays) {
        this.restockableInDays = restockableInDays;
        return this;
    }

    public AvailabilityInfoBuilder availableQuantity(@Nullable final Long availableQuantity) {
        this.availableQuantity = availableQuantity;
        return this;
    }

    @Override
    public AvailabilityInfo build() {
        return new AvailabilityInfoImpl(isOnStock, restockableInDays, availableQuantity);
    }

    @Nullable
    public Long getAvailableQuantity() {
        return availableQuantity;
    }

    @Nullable
    public Boolean isOnStock() {
        return isOnStock;
    }

    @Nullable
    public Integer getRestockableInDays() {
        return restockableInDays;
    }
}
