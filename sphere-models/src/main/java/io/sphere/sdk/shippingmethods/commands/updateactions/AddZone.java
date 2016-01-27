package io.sphere.sdk.shippingmethods.commands.updateactions;

import io.sphere.sdk.commands.UpdateActionImpl;
import io.sphere.sdk.models.Reference;
import io.sphere.sdk.models.Referenceable;
import io.sphere.sdk.shippingmethods.ShippingMethod;
import io.sphere.sdk.zones.Zone;

/**
 * Adds a zone.
 *
 * {@doc.gen intro}
 *
 * {@include.example io.sphere.sdk.shippingmethods.commands.ShippingMethodUpdateCommandTest#workingWithZones()}
 *
 * @see ShippingMethod#getZones()
 */
public class AddZone extends UpdateActionImpl<ShippingMethod> {
    private final Reference<Zone> zone;

    private AddZone(final Reference<Zone> zone) {
        super("addZone");
        this.zone = zone;
    }

    public Reference<Zone> getZone() {
        return zone;
    }

    public static AddZone of(final Referenceable<Zone> zone) {
        return new AddZone(zone.toReference());
    }
}
