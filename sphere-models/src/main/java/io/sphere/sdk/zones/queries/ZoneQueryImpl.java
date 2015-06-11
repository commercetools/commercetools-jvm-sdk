package io.sphere.sdk.zones.queries;

import io.sphere.sdk.queries.MetaModelQueryDslBuilder;
import io.sphere.sdk.queries.MetaModelQueryDslImpl;
import io.sphere.sdk.zones.Zone;
import io.sphere.sdk.zones.expansion.ZoneExpansionModel;

final class ZoneQueryImpl extends MetaModelQueryDslImpl<Zone, ZoneQuery, ZoneQueryModel, ZoneExpansionModel<Zone>> implements ZoneQuery {
    ZoneQueryImpl(){
        super(ZoneEndpoint.ENDPOINT.endpoint(), ZoneQuery.resultTypeReference(), ZoneQueryModel.of(), ZoneExpansionModel.of(), ZoneQueryImpl::new);
    }

    private ZoneQueryImpl(final MetaModelQueryDslBuilder<Zone, ZoneQuery, ZoneQueryModel, ZoneExpansionModel<Zone>> builder) {
        super(builder);
    }
}
