package io.sphere.sdk.shippingmethods.queries;

import io.sphere.sdk.queries.QueryModel;
import io.sphere.sdk.queries.QueryModelImpl;
import io.sphere.sdk.queries.ReferenceQueryModel;
import io.sphere.sdk.zones.Zone;

final class ZoneRateCollectionQueryModelImpl<T> extends QueryModelImpl<T> implements ZoneRateCollectionQueryModel<T> {
    ZoneRateCollectionQueryModelImpl(QueryModel<T> parent, String pathSegment) {
        super(parent, pathSegment);
    }

    @Override
    public ReferenceQueryModel<T, Zone> zone() {
        return referenceModel("zone");
    }
}
