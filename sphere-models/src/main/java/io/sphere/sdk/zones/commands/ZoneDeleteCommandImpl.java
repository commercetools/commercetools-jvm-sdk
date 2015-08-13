package io.sphere.sdk.zones.commands;

import io.sphere.sdk.commands.MetaModelByIdDeleteCommandBuilder;
import io.sphere.sdk.commands.MetaModelByIdDeleteCommandImpl;
import io.sphere.sdk.models.Versioned;
import io.sphere.sdk.zones.Zone;
import io.sphere.sdk.zones.expansion.ZoneExpansionModel;

final class ZoneDeleteCommandImpl extends MetaModelByIdDeleteCommandImpl<Zone, ZoneDeleteCommand, ZoneExpansionModel<Zone>> implements ZoneDeleteCommand {
    ZoneDeleteCommandImpl(final Versioned<Zone> versioned) {
        super(versioned, ZoneEndpoint.ENDPOINT, ZoneExpansionModel.of(), ZoneDeleteCommandImpl::new);
    }


    ZoneDeleteCommandImpl(final MetaModelByIdDeleteCommandBuilder<Zone, ZoneDeleteCommand, ZoneExpansionModel<Zone>> builder) {
        super(builder);
    }
}
