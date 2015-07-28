package io.sphere.sdk.zones.commands;

import io.sphere.sdk.commands.UpdateAction;
import io.sphere.sdk.commands.UpdateCommandDslImpl;
import io.sphere.sdk.models.Versioned;
import io.sphere.sdk.zones.Zone;

import java.util.List;

final class ZoneUpdateCommandImpl extends UpdateCommandDslImpl<Zone, ZoneUpdateCommand> implements ZoneUpdateCommand {
    ZoneUpdateCommandImpl(final Versioned<Zone> versioned, final List<? extends UpdateAction<Zone>> updateActions) {
        super(versioned, updateActions, ZoneEndpoint.ENDPOINT);
    }
}
