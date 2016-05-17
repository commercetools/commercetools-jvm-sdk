package io.sphere.sdk.shippingmethods;

import com.fasterxml.jackson.annotation.JsonCreator;
import io.sphere.sdk.models.Base;
import io.sphere.sdk.models.Reference;
import io.sphere.sdk.zones.Zone;

import java.util.List;

/**
 * Defines shipping rates (prices) for a specific zone.
 *
 * @see ShippingMethod#getZoneRates()
 * @see ShippingMethodDraft#getZoneRates()
 */
final class ZoneRateImpl extends Base implements ZoneRate {
    private final Reference<Zone> zone;
    private final List<ShippingRate> shippingRates;

    @JsonCreator
    ZoneRateImpl(final Reference<Zone> zone, final List<ShippingRate> shippingRates) {
        this.zone = zone;
        this.shippingRates = shippingRates;
    }

    public Reference<Zone> getZone() {
        return zone;
    }

    public List<ShippingRate> getShippingRates() {
        return shippingRates;
    }
}
