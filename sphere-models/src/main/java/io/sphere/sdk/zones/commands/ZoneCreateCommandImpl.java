package io.sphere.sdk.zones.commands;

import io.sphere.sdk.commands.CreateCommandImpl;
import io.sphere.sdk.zones.Zone;
import io.sphere.sdk.zones.ZoneDraft;

final class ZoneCreateCommandImpl extends CreateCommandImpl<Zone, ZoneDraft> implements ZoneCreateCommand {

    ZoneCreateCommandImpl(final ZoneDraft draft) {
        super(draft, ZoneEndpoint.ENDPOINT);
    }
}
