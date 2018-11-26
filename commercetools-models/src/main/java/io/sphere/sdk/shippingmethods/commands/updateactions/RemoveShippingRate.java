package io.sphere.sdk.shippingmethods.commands.updateactions;

import io.sphere.sdk.commands.UpdateActionImpl;
import io.sphere.sdk.models.Reference;
import io.sphere.sdk.models.Referenceable;
import io.sphere.sdk.models.ResourceIdentifier;
import io.sphere.sdk.shippingmethods.ShippingMethod;
import io.sphere.sdk.shippingmethods.ShippingRate;
import io.sphere.sdk.zones.Zone;

/**
 * Removes a shipping rate.
 *
 * {@doc.gen intro}
 *
 * {@include.example io.sphere.sdk.shippingmethods.commands.ShippingMethodUpdateCommandIntegrationTest#workingWithZones()}
 */
public final class RemoveShippingRate extends UpdateActionImpl<ShippingMethod> {
    private final ResourceIdentifier<Zone> zone;
    private final ShippingRate shippingRate;

    private RemoveShippingRate(final ShippingRate shippingRate, final ResourceIdentifier<Zone> zone) {
        super("removeShippingRate");
        this.shippingRate = shippingRate;
        this.zone = zone;
    }

    public ShippingRate getShippingRate() {
        return shippingRate;
    }

    public ResourceIdentifier<Zone> getZone() {
        return zone;
    }

    public static RemoveShippingRate of(final ShippingRate shippingRate, final Referenceable<Zone> zone) {
        return new RemoveShippingRate(shippingRate, zone.toReference());
    }
}
