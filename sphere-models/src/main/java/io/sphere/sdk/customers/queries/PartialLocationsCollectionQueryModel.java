package io.sphere.sdk.customers.queries;

import io.sphere.sdk.queries.QueryModel;

public class PartialLocationsCollectionQueryModel extends LocationsCollectionQueryModel<PartialLocationsCollectionQueryModel> {
    private PartialLocationsCollectionQueryModel(final QueryModel<PartialLocationsCollectionQueryModel> parent, final String pathSegment) {
        super(parent, pathSegment);
    }

    static PartialLocationsCollectionQueryModel of() {
        return new PartialLocationsCollectionQueryModel(null, null);
    }
}
