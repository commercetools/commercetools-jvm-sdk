package io.sphere.sdk.inventory;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import javax.annotation.Nullable;

/**
 * Infos about availability.
 *
 * @see AvailabilityInfoBuilder
 */
@JsonDeserialize(as = AvailabilityInfoImpl.class)
public interface AvailabilityInfo {
    @Nullable
    Boolean isOnStock();

    @Nullable
    Integer getRestockableInDays();

    Long getAvailableQuantity();
}
