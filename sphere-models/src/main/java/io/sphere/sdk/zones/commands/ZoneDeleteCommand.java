package io.sphere.sdk.zones.commands;

import io.sphere.sdk.commands.ByIdDeleteCommand;
import io.sphere.sdk.expansion.MetaModelExpansionDsl;
import io.sphere.sdk.models.Versioned;
import io.sphere.sdk.zones.Zone;
import io.sphere.sdk.zones.expansion.ZoneExpansionModel;

public interface ZoneDeleteCommand extends ByIdDeleteCommand<Zone>, MetaModelExpansionDsl<Zone, ZoneDeleteCommand, ZoneExpansionModel<Zone>> {
    static ZoneDeleteCommand of(final Versioned<Zone> versioned) {
        return new ZoneDeleteCommandImpl(versioned);
    }
}
