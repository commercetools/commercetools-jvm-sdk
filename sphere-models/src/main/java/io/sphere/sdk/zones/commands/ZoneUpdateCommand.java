package io.sphere.sdk.zones.commands;

import io.sphere.sdk.commands.UpdateAction;
import io.sphere.sdk.commands.UpdateCommandDsl;
import io.sphere.sdk.expansion.MetaModelReferenceExpansionDsl;
import io.sphere.sdk.models.Versioned;
import io.sphere.sdk.zones.Zone;
import io.sphere.sdk.zones.expansion.ZoneExpansionModel;

import java.util.Collections;
import java.util.List;

/**
 {@doc.gen list actions}
 */
public interface ZoneUpdateCommand extends UpdateCommandDsl<Zone, ZoneUpdateCommand>, MetaModelReferenceExpansionDsl<Zone, ZoneUpdateCommand, ZoneExpansionModel<Zone>> {
    static ZoneUpdateCommand of(final Versioned<Zone> versioned, final UpdateAction<Zone> updateAction) {
        return of(versioned, Collections.singletonList(updateAction));
    }

    static ZoneUpdateCommand of(final Versioned<Zone> versioned, final List<? extends UpdateAction<Zone>> updateActions) {
        return new ZoneUpdateCommandImpl(versioned, updateActions);
    }
}
