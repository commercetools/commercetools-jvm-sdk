package io.sphere.sdk.zones.queries;

import io.sphere.sdk.queries.UltraQueryDslBuilder;
import io.sphere.sdk.queries.UltraQueryDslImpl;
import io.sphere.sdk.zones.Zone;
import io.sphere.sdk.zones.expansion.ZoneExpansionModel;

final class ZoneQueryImpl  extends UltraQueryDslImpl<Zone, ZoneQuery, ZoneQueryModel<Zone>, ZoneExpansionModel<Zone>> implements ZoneQuery {
    ZoneQueryImpl(){
        super(ZoneEndpoint.ENDPOINT.endpoint(), ZoneQuery.resultTypeReference(), ZoneQueryModel.of(), ZoneExpansionModel.of(), ZoneQueryImpl::new);
    }

    private ZoneQueryImpl(final UltraQueryDslBuilder<Zone, ZoneQuery, ZoneQueryModel<Zone>, ZoneExpansionModel<Zone>> builder) {
        super(builder);
    }
}
