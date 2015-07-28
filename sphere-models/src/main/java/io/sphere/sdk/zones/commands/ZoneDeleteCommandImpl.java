package io.sphere.sdk.zones.commands;

import io.sphere.sdk.commands.ByIdDeleteCommandImpl;
import io.sphere.sdk.commands.DeleteCommand;
import io.sphere.sdk.models.Versioned;
import io.sphere.sdk.zones.Zone;

public class ZoneDeleteCommandImpl extends ByIdDeleteCommandImpl<Zone> {
    private ZoneDeleteCommandImpl(final Versioned<Zone> versioned) {
        super(versioned, ZoneEndpoint.ENDPOINT);
    }

    public static DeleteCommand<Zone> of(final Versioned<Zone> versioned) {
        return new ZoneDeleteCommandImpl(versioned);
    }
}
