package io.sphere.sdk.zones.commands;

import io.sphere.sdk.commands.CreateCommandImpl;
import io.sphere.sdk.zones.Zone;
import io.sphere.sdk.zones.ZoneDraft;


/**
 * Creates a zone in SPHERE.IO.
 *
 * {@include.example io.sphere.sdk.zones.commands.ZoneCreateCommandTest#execution()}
 */
public class ZoneCreateCommand extends CreateCommandImpl<Zone, ZoneDraft> {

    private ZoneCreateCommand(final ZoneDraft draft) {
        super(draft, ZoneEndpoint.ENDPOINT);
    }

    public static ZoneCreateCommand of(final ZoneDraft draft) {
        return new ZoneCreateCommand(draft);
    }
}
