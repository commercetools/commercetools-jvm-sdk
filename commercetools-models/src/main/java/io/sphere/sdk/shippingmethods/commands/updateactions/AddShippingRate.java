package io.sphere.sdk.shippingmethods.commands.updateactions;

import io.sphere.sdk.commands.UpdateActionImpl;
import io.sphere.sdk.models.Reference;
import io.sphere.sdk.models.Referenceable;
import io.sphere.sdk.models.ResourceIdentifier;
import io.sphere.sdk.shippingmethods.ShippingMethod;
import io.sphere.sdk.shippingmethods.ShippingRate;
import io.sphere.sdk.zones.Zone;

/**
 * Adds a shipping rate.
 *
 * {@doc.gen intro}
 *
 * {@include.example io.sphere.sdk.shippingmethods.commands.ShippingMethodUpdateCommandIntegrationTest#workingWithZones()}
 *
 * @see ShippingMethod#getShippingRatesForZone(Referenceable)
 * @see ShippingMethod#getZoneRates()
 */
public final class AddShippingRate extends UpdateActionImpl<ShippingMethod> {
    private final ResourceIdentifier<Zone> zone;
    private final ShippingRate shippingRate;

    private AddShippingRate(final ShippingRate shippingRate, final ResourceIdentifier<Zone> zone) {
        super("addShippingRate");
        this.shippingRate = shippingRate;
        this.zone = zone;
    }

    public ShippingRate getShippingRate() {
        return shippingRate;
    }

    public ResourceIdentifier<Zone> getZone() {
        return zone;
    }

    public static AddShippingRate of(final ShippingRate shippingRate, final Referenceable<Zone> zone) {
        return new AddShippingRate(shippingRate, zone.toReference());
    }
}
