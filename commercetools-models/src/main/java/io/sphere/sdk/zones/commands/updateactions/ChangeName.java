package io.sphere.sdk.zones.commands.updateactions;

import io.sphere.sdk.commands.UpdateActionImpl;
import io.sphere.sdk.zones.Zone;

/**
 * Updates the name of a zone.
 *
 * {@doc.gen intro}
 *
 * {@include.example io.sphere.sdk.zones.commands.ZoneUpdateCommandIntegrationTest#changeName()}
 */
public final class ChangeName extends UpdateActionImpl<Zone> {
    private final String name;

    private ChangeName(final String name) {
        super("changeName");
        this.name = name;
    }

    public static ChangeName of(final String name) {
        return new ChangeName(name);
    }

    public String getName() {
        return name;
    }
}
