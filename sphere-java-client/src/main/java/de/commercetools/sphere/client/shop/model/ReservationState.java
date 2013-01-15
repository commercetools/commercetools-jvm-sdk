package de.commercetools.sphere.client.shop.model;

import org.codehaus.jackson.annotate.JsonProperty;
import org.joda.time.DateTime;

public class ReservationState {
    private @JsonProperty("inventoryId") String inventoryId;
    private @JsonProperty("isAvailable") boolean isAvailable;
    private int delayedAvailabilityInDays;
    private @JsonProperty("createdAt") DateTime createdAt;

    // for JSON deserializer
    private ReservationState() {}

    /** Indicates that the line item is available in the desired quantity
     * (but the availability can be delayed). */
    public boolean isAvailable() { return isAvailable; }

    /** The number of days until the item is available. */
    public int getDelayedAvailabilityInDays() { return delayedAvailabilityInDays; }
}
