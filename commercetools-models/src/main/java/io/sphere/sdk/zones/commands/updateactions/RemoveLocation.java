package io.sphere.sdk.zones.commands.updateactions;

import io.sphere.sdk.commands.UpdateActionImpl;
import io.sphere.sdk.zones.Location;
import io.sphere.sdk.zones.Zone;

/**
 * Removes a location from a zone.
 *
 * {@doc.gen intro}
 *
 * {@include.example io.sphere.sdk.zones.commands.ZoneUpdateCommandIntegrationTest#addLocationAndRemoveLocation()}
 */
public final class RemoveLocation extends UpdateActionImpl<Zone> {
    private final Location location;

    private RemoveLocation(final Location location) {
        super("removeLocation");
        this.location = location;
    }

    public static RemoveLocation of(final Location location) {
        return new RemoveLocation(location);
    }

    public Location getLocation() {
        return location;
    }
}
