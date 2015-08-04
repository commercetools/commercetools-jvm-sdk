package io.sphere.sdk.zones.queries;

import io.sphere.sdk.queries.MetaModelFetchDslBuilder;
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

    public ZoneByIdGetImpl(MetaModelFetchDslBuilder<Zone, Zone, ZoneByIdGet, ZoneExpansionModel<Zone>> builder) {
        super(builder);
    }
}
