package io.sphere.sdk.zones.queries;

import io.sphere.sdk.queries.QueryModel;

final class PartialLocationsCollectionQueryModelImpl extends LocationsCollectionQueryModelImpl<PartialLocationsCollectionQueryModel> implements PartialLocationsCollectionQueryModel {
    private PartialLocationsCollectionQueryModelImpl(final QueryModel<PartialLocationsCollectionQueryModel> parent, final String pathSegment) {
        super(parent, pathSegment);
    }

    static PartialLocationsCollectionQueryModel of() {
        return new PartialLocationsCollectionQueryModelImpl(null, null);
    }
}
