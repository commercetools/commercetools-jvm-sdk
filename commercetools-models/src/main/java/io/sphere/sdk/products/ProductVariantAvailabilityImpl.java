package io.sphere.sdk.products;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.sphere.sdk.inventory.AvailabilityInfo;
import io.sphere.sdk.models.Base;

import javax.annotation.Nullable;
import java.util.Collections;
import java.util.Map;

final class ProductVariantAvailabilityImpl extends Base implements ProductVariantAvailability {
    @JsonProperty("isOnStock")
    private final Boolean isOnStock;
    @Nullable
    private final Integer restockableInDays;
    @Nullable
    private final Map<String, AvailabilityInfo> channels;
    @Nullable
    private final Long availableQuantity;

    @JsonCreator
    ProductVariantAvailabilityImpl(final Boolean isOnStock, @Nullable final Integer restockableInDays, final Map<String, AvailabilityInfo> channels, final Long availableQuantity) {
        this.isOnStock = isOnStock;
        this.restockableInDays = restockableInDays;
        this.channels = channels != null ? channels : Collections.emptyMap();
        this.availableQuantity = availableQuantity;
    }

    public Boolean isOnStock() {
        return isOnStock;
    }

    @Nullable
    public Integer getRestockableInDays() {
        return restockableInDays;
    }

    @Override
    @Nullable
    public Map<String, AvailabilityInfo> getChannels() {
        return channels;
    }

    public Boolean getOnStock() {
        return isOnStock;
    }

    @Override
    @Nullable
    public Long getAvailableQuantity() {
        return availableQuantity;
    }
}
