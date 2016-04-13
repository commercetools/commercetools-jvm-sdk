package io.sphere.sdk.products;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.sphere.sdk.inventory.AvailabilityInfo;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Map;

@JsonDeserialize(as = ProductVariantAvailabilityImpl.class)
public interface ProductVariantAvailability {
    @Nullable
    Boolean isOnStock();

    @Nullable
    Integer getRestockableInDays();

    @Nullable
    Long getAvailableQuantity();

    @Nonnull
    Map<String, AvailabilityInfo> getChannels();
}
