package io.sphere.sdk.shippingmethods;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.sphere.sdk.annotations.ResourceValue;
import io.sphere.sdk.models.Reference;
import io.sphere.sdk.models.Referenceable;
import io.sphere.sdk.zones.Zone;

import java.util.List;

/**
 * Defines shipping rates (prices) for a specific zone.
 *
 * @see ShippingMethod#getZoneRates()
 * @see ShippingMethodDraft#getZoneRates()
 */
@ResourceValue
@JsonDeserialize(as = ZoneRateImpl.class)
public interface ZoneRate {

    Reference<Zone> getZone();

    List<ShippingRate> getShippingRates();

    @JsonIgnore
    static ZoneRate of(final Referenceable<Zone> zone, final List<ShippingRate> shippingRates) {
        return new ZoneRateImpl(shippingRates, zone.toReference());
    }
}
