package io.sphere.sdk.zones.commands;

import io.sphere.sdk.commands.MetaModelCreateCommandBuilder;
import io.sphere.sdk.commands.MetaModelCreateCommandImpl;
import io.sphere.sdk.zones.Zone;
import io.sphere.sdk.zones.ZoneDraft;
import io.sphere.sdk.zones.expansion.ZoneExpansionModel;

final class ZoneCreateCommandImpl extends MetaModelCreateCommandImpl<Zone, ZoneCreateCommand, ZoneDraft, ZoneExpansionModel<Zone>> implements ZoneCreateCommand {
    ZoneCreateCommandImpl(final MetaModelCreateCommandBuilder<Zone, ZoneCreateCommand, ZoneDraft, ZoneExpansionModel<Zone>> builder) {
        super(builder);
    }

    ZoneCreateCommandImpl(final ZoneDraft draft) {
        super(draft, ZoneEndpoint.ENDPOINT, ZoneExpansionModel.of(), ZoneCreateCommandImpl::new);
    }
}
