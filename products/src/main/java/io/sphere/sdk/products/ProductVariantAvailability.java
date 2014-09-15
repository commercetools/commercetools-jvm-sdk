package io.sphere.sdk.products;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import java.util.Optional;

@JsonDeserialize(as = ProductVariantAvailabilityImpl.class)
public interface ProductVariantAvailability {
    public boolean isOnStock();

    public Optional<Integer> getRestockableInDays();
}
