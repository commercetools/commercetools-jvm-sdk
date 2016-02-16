package io.sphere.sdk.shippingmethods.commands.updateactions;

import io.sphere.sdk.commands.UpdateActionImpl;
import io.sphere.sdk.models.Reference;
import io.sphere.sdk.models.Referenceable;
import io.sphere.sdk.shippingmethods.ShippingMethod;
import io.sphere.sdk.zones.Zone;

/**
 * Removes a zone.
 *
 * {@doc.gen intro}
 *
 * {@include.example io.sphere.sdk.shippingmethods.commands.ShippingMethodUpdateCommandIntegrationTest#workingWithZones()}
 */
public final class RemoveZone extends UpdateActionImpl<ShippingMethod> {
    private final Reference<Zone> zone;

    private RemoveZone(final Reference<Zone> zone) {
        super("removeZone");
        this.zone = zone;
    }

    public Reference<Zone> getZone() {
        return zone;
    }

    public static RemoveZone of(final Referenceable<Zone> zone) {
        return new RemoveZone(zone.toReference());
    }
}
