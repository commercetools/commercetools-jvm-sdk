package io.sphere.sdk.zones.queries;

import io.sphere.sdk.queries.DefaultModelQueryModelImpl;
import io.sphere.sdk.queries.QueryModel;
import io.sphere.sdk.queries.StringQuerySortingModel;
import io.sphere.sdk.zones.Zone;

public class ZoneQueryModel extends DefaultModelQueryModelImpl<Zone> {
    public static ZoneQueryModel of() {
        return new ZoneQueryModel(null, null);
    }

    private ZoneQueryModel(final QueryModel<Zone> parent, final String pathSegment) {
        super(parent, pathSegment);
    }

    public StringQuerySortingModel<Zone> name() {
        return stringModel("name");
    }

    public LocationsCollectionQueryModel<Zone> locations() {
        return new LocationsCollectionQueryModelImpl<>(this, "locations");
    }
}
