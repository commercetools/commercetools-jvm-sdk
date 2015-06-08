package io.sphere.sdk.zones.queries;

import io.sphere.sdk.queries.MetaModelFetchDslBuilder;
import io.sphere.sdk.queries.MetaModelFetchDslImpl;
import io.sphere.sdk.zones.Zone;
import io.sphere.sdk.zones.expansion.ZoneExpansionModel;

/**
 Gets a zone by ID.

 {@include.example io.sphere.sdk.zones.queries.ZoneByIdFetchTest#fetchById()}
 */
public class ZoneByIdFetchImpl extends MetaModelFetchDslImpl<Zone, ZoneByIdFetch, ZoneExpansionModel<Zone>> implements ZoneByIdFetch {
    ZoneByIdFetchImpl(final String id) {
        super(id, ZoneEndpoint.ENDPOINT, ZoneExpansionModel.of(), ZoneByIdFetchImpl::new);
    }

    public ZoneByIdFetchImpl(MetaModelFetchDslBuilder<Zone, ZoneByIdFetch, ZoneExpansionModel<Zone>> builder) {
        super(builder);
    }
}
