package io.sphere.sdk.zones.queries;

import io.sphere.sdk.queries.ResourceQueryModelImpl;
import io.sphere.sdk.queries.QueryModel;
import io.sphere.sdk.queries.StringQuerySortingModel;
import io.sphere.sdk.zones.Zone;

final class ZoneQueryModelImpl extends ResourceQueryModelImpl<Zone> implements ZoneQueryModel {

    ZoneQueryModelImpl(final QueryModel<Zone> parent, final String pathSegment) {
        super(parent, pathSegment);
    }

    @Override
    public StringQuerySortingModel<Zone> name() {
        return stringModel("name");
    }

    @Override
    public LocationsCollectionQueryModel<Zone> locations() {
        return new LocationsCollectionQueryModelImpl<>(this, "locations");
    }
}
