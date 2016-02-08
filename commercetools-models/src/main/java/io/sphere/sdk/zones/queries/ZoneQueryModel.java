package io.sphere.sdk.zones.queries;

import io.sphere.sdk.queries.ResourceQueryModel;
import io.sphere.sdk.queries.StringQuerySortingModel;
import io.sphere.sdk.zones.Zone;

public interface ZoneQueryModel extends ResourceQueryModel<Zone> {
    StringQuerySortingModel<Zone> name();

    LocationsCollectionQueryModel<Zone> locations();

    static ZoneQueryModel of() {
        return new ZoneQueryModelImpl(null, null);
    }
}
