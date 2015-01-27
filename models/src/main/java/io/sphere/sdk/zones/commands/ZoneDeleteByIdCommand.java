package io.sphere.sdk.zones.commands;

import io.sphere.sdk.commands.DeleteByIdCommandImpl;
import io.sphere.sdk.models.Versioned;
import io.sphere.sdk.zones.Zone;

public class ZoneDeleteByIdCommand extends DeleteByIdCommandImpl<Zone> {
    private ZoneDeleteByIdCommand(final Versioned<Zone> versioned) {
        super(versioned, ZonesEndpoint.ENDPOINT);
    }

    public static ZoneDeleteByIdCommand of(final Versioned<Zone> versioned) {
        return new ZoneDeleteByIdCommand(versioned);
    }
}
