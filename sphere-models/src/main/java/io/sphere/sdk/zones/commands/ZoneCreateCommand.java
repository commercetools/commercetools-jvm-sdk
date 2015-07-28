package io.sphere.sdk.zones.commands;

import io.sphere.sdk.commands.CreateCommand;
import io.sphere.sdk.zones.Zone;
import io.sphere.sdk.zones.ZoneDraft;


/**
 * Creates a zone in SPHERE.IO.
 *
 * {@include.example io.sphere.sdk.zones.commands.ZoneCreateCommandTest#execution()}
 */
public interface ZoneCreateCommand extends CreateCommand<Zone> {

    static ZoneCreateCommand of(final ZoneDraft draft) {
        return new ZoneCreateCommandImpl(draft);
    }
}
