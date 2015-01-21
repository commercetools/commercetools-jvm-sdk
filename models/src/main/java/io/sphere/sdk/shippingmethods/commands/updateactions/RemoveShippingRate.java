package io.sphere.sdk.shippingmethods.commands.updateactions;

import io.sphere.sdk.commands.UpdateAction;
import io.sphere.sdk.models.Reference;
import io.sphere.sdk.models.Referenceable;
import io.sphere.sdk.shippingmethods.ShippingMethod;
import io.sphere.sdk.shippingmethods.ShippingRate;
import io.sphere.sdk.zones.Zone;

/**
 *
 * {@include.example io.sphere.sdk.shippingmethods.commands.ShippingMethodUpdateCommandTest#workingWithZones()}
 */
public class RemoveShippingRate extends UpdateAction<ShippingMethod> {
    private final Reference<Zone> zone;
    private final ShippingRate shippingRate;

    private RemoveShippingRate(final ShippingRate shippingRate, final Reference<Zone> zone) {
        super("removeShippingRate");
        this.shippingRate = shippingRate;
        this.zone = zone;
    }

    public ShippingRate getShippingRate() {
        return shippingRate;
    }

    public Reference<Zone> getZone() {
        return zone;
    }

    public static RemoveShippingRate of(final ShippingRate shippingRate, final Referenceable<Zone> zone) {
        return new RemoveShippingRate(shippingRate, zone.toReference());
    }
}
