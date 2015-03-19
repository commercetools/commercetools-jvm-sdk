package io.sphere.sdk.zones.commands;

import io.sphere.sdk.commands.ByIdDeleteCommandImpl;
import io.sphere.sdk.commands.DeleteCommand;
import io.sphere.sdk.models.Versioned;
import io.sphere.sdk.zones.Zone;

public class ZoneDeleteCommand extends ByIdDeleteCommandImpl<Zone> {
    private ZoneDeleteCommand(final Versioned<Zone> versioned) {
        super(versioned, ZonesEndpoint.ENDPOINT);
    }

    public static DeleteCommand<Zone> of(final Versioned<Zone> versioned) {
        return new ZoneDeleteCommand(versioned);
    }
}
