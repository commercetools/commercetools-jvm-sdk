package io.sphere.sdk.zones.commands;

import io.sphere.sdk.commands.CreateCommand;
import io.sphere.sdk.commands.DraftBasedCreateCommand;
import io.sphere.sdk.expansion.MetaModelReferenceExpansionDsl;
import io.sphere.sdk.zones.Zone;
import io.sphere.sdk.zones.ZoneDraft;
import io.sphere.sdk.zones.expansion.ZoneExpansionModel;


/**
 * Creates a zone in SPHERE.IO.
 *
 * {@include.example io.sphere.sdk.zones.commands.ZoneCreateCommandIntegrationTest#execution()}
 */
public interface ZoneCreateCommand extends DraftBasedCreateCommand<Zone, ZoneDraft>, MetaModelReferenceExpansionDsl<Zone, ZoneCreateCommand, ZoneExpansionModel<Zone>> {

    static ZoneCreateCommand of(final ZoneDraft draft) {
        return new ZoneCreateCommandImpl(draft);
    }
}
