package io.sphere.sdk.zones.queries;

import io.sphere.sdk.queries.MetaModelGetDslBuilder;
import io.sphere.sdk.queries.MetaModelGetDslImpl;
import io.sphere.sdk.zones.Zone;
import io.sphere.sdk.zones.expansion.ZoneExpansionModel;

/**
 Gets a zone by ID.

 {@include.example io.sphere.sdk.zones.queries.ZoneByIdGetTest#fetchById()}
 */
final class ZoneByIdGetImpl extends MetaModelGetDslImpl<Zone, Zone, ZoneByIdGet, ZoneExpansionModel<Zone>> implements ZoneByIdGet {
    ZoneByIdGetImpl(final String id) {
        super(id, ZoneEndpoint.ENDPOINT, ZoneExpansionModel.of(), ZoneByIdGetImpl::new);
    }

    public ZoneByIdGetImpl(MetaModelGetDslBuilder<Zone, Zone, ZoneByIdGet, ZoneExpansionModel<Zone>> builder) {
        super(builder);
    }
}
