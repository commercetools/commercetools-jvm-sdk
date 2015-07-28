package io.sphere.sdk.zones.commands;

import io.sphere.sdk.commands.UpdateAction;
import io.sphere.sdk.commands.UpdateCommandDslImpl;
import io.sphere.sdk.models.Versioned;
import io.sphere.sdk.zones.Zone;

import java.util.List;

import static java.util.Arrays.asList;

/**
 {@doc.gen list actions}
 */
public class ZoneUpdateCommand extends UpdateCommandDslImpl<Zone, ZoneUpdateCommand> {
    private ZoneUpdateCommand(final Versioned<Zone> versioned, final List<? extends UpdateAction<Zone>> updateActions) {
        super(versioned, updateActions, ZoneEndpoint.ENDPOINT);
    }

    public static ZoneUpdateCommand of(final Versioned<Zone> versioned, final UpdateAction<Zone> updateAction) {
        return of(versioned, asList(updateAction));
    }

    public static ZoneUpdateCommand of(final Versioned<Zone> versioned, final List<? extends UpdateAction<Zone>> updateActions) {
        return new ZoneUpdateCommand(versioned, updateActions);
    }
}
