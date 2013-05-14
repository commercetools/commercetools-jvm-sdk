package io.sphere.client.shop.model;

import org.codehaus.jackson.annotate.JsonProperty;

// TODO The reason for this class?

/** Availability of a variant in a product catalog. */
public class VariantAvailability {
    @JsonProperty("isOnStock") private boolean isOnStock;
    private int restockableInDays;

    // for JSON deserializer
    private VariantAvailability() { }

    public boolean isOnStock() { return isOnStock; }

    public int getRestockableInDays() { return restockableInDays; }
}
