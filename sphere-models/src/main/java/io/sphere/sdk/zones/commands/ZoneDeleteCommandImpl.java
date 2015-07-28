package io.sphere.sdk.zones.commands;

import io.sphere.sdk.commands.ByIdDeleteCommandImpl;
import io.sphere.sdk.models.Versioned;
import io.sphere.sdk.zones.Zone;

final class ZoneDeleteCommandImpl extends ByIdDeleteCommandImpl<Zone> {
    ZoneDeleteCommandImpl(final Versioned<Zone> versioned) {
        super(versioned, ZoneEndpoint.ENDPOINT);
    }
}
