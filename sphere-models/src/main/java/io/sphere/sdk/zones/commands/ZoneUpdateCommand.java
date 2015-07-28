package io.sphere.sdk.zones.commands;

import io.sphere.sdk.commands.UpdateAction;
import io.sphere.sdk.commands.UpdateCommandDsl;
import io.sphere.sdk.models.Versioned;
import io.sphere.sdk.zones.Zone;

import java.util.List;

import static java.util.Arrays.asList;

/**
 {@doc.gen list actions}
 */
public interface ZoneUpdateCommand extends UpdateCommandDsl<Zone, ZoneUpdateCommand> {
    static ZoneUpdateCommand of(final Versioned<Zone> versioned, final UpdateAction<Zone> updateAction) {
        return of(versioned, asList(updateAction));
    }

    static ZoneUpdateCommand of(final Versioned<Zone> versioned, final List<? extends UpdateAction<Zone>> updateActions) {
        return new ZoneUpdateCommandImpl(versioned, updateActions);
    }
}
