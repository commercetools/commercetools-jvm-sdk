package io.sphere.sdk.zones.commands;

import io.sphere.sdk.commands.*;
import io.sphere.sdk.models.Versioned;
import io.sphere.sdk.zones.Zone;
import io.sphere.sdk.zones.expansion.ZoneExpansionModel;

import java.util.List;

final class ZoneUpdateCommandImpl extends MetaModelUpdateCommandDslImpl<Zone, ZoneUpdateCommand, ZoneExpansionModel<Zone>> implements ZoneUpdateCommand {
    ZoneUpdateCommandImpl(final Versioned<Zone> versioned, final List<? extends UpdateAction<Zone>> updateActions) {
        super(versioned, updateActions, ZoneEndpoint.ENDPOINT, ZoneUpdateCommandImpl::new, ZoneExpansionModel.of());
    }

    ZoneUpdateCommandImpl(final MetaModelUpdateCommandDslBuilder<Zone, ZoneUpdateCommand, ZoneExpansionModel<Zone>> builder) {
        super(builder);
    }
}
