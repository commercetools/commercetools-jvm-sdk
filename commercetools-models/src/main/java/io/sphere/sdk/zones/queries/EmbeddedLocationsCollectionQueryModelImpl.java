package io.sphere.sdk.zones.queries;

import io.sphere.sdk.queries.QueryModel;

final class EmbeddedLocationsCollectionQueryModelImpl extends LocationsCollectionQueryModelImpl<EmbeddedLocationsCollectionQueryModel> implements EmbeddedLocationsCollectionQueryModel {
    private EmbeddedLocationsCollectionQueryModelImpl(final QueryModel<EmbeddedLocationsCollectionQueryModel> parent, final String pathSegment) {
        super(parent, pathSegment);
    }

    static EmbeddedLocationsCollectionQueryModel of() {
        return new EmbeddedLocationsCollectionQueryModelImpl(null, null);
    }
}
