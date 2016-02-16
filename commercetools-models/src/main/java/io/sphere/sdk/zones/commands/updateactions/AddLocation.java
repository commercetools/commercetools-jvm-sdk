package io.sphere.sdk.zones.commands.updateactions;

import io.sphere.sdk.commands.UpdateActionImpl;
import io.sphere.sdk.zones.Location;
import io.sphere.sdk.zones.Zone;

/**
 * Adds a location to a zone.
 *
 * {@doc.gen intro}
 *
 * {@include.example io.sphere.sdk.zones.commands.ZoneUpdateCommandIntegrationTest#addLocationAndRemoveLocation()}
 *
 * @see Zone#getLocations()
 */
public final class AddLocation extends UpdateActionImpl<Zone> {
    private final Location location;

    private AddLocation(final Location location) {
        super("addLocation");
        this.location = location;
    }

    public static AddLocation of(final Location location) {
        return new AddLocation(location);
    }

    public Location getLocation() {
        return location;
    }
}
