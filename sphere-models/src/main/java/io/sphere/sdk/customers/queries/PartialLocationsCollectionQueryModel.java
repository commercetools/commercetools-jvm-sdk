package io.sphere.sdk.customers.queries;

import io.sphere.sdk.queries.QueryModel;

import java.util.Optional;

public class PartialLocationsCollectionQueryModel extends LocationsCollectionQueryModel<PartialLocationsCollectionQueryModel> {
    private PartialLocationsCollectionQueryModel(final Optional<? extends QueryModel<PartialLocationsCollectionQueryModel>> parent, final Optional<String> pathSegment) {
        super(parent, pathSegment);
    }

    static PartialLocationsCollectionQueryModel of() {
        return new PartialLocationsCollectionQueryModel(Optional.empty(), Optional.empty());
    }
}
