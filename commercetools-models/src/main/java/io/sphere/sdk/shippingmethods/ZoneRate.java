package io.sphere.sdk.shippingmethods;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.sphere.sdk.models.Base;
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
public final class ZoneRate extends Base {
    private final Reference<Zone> zone;
    private final List<ShippingRate> shippingRates;

    @JsonCreator
    private ZoneRate(final Reference<Zone> zone, final List<ShippingRate> shippingRates) {
        this.zone = zone;
        this.shippingRates = shippingRates;
    }

    /**
     * Creates a new {@link ZoneRate}.
     *
     * @param zone zone for the rates to apply
     * @param shippingRates costs for different currencies
     * @return zone rate
     */
    @JsonIgnore
    public static ZoneRate of(final Referenceable<Zone> zone, final List<ShippingRate> shippingRates) {
        return new ZoneRate(zone.toReference(), shippingRates);
    }

    public Reference<Zone> getZone() {
        return zone;
    }

    public List<ShippingRate> getShippingRates() {
        return shippingRates;
    }
}
