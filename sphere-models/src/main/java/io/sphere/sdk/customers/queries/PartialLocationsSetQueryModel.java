package io.sphere.sdk.customers.queries;

import io.sphere.sdk.queries.QueryModel;

import java.util.Optional;

public class PartialLocationsSetQueryModel extends LocationsSetQueryModel<PartialLocationsSetQueryModel> {
    private PartialLocationsSetQueryModel(final Optional<? extends QueryModel<PartialLocationsSetQueryModel>> parent, final Optional<String> pathSegment) {
        super(parent, pathSegment);
    }

    static PartialLocationsSetQueryModel get() {
        return new PartialLocationsSetQueryModel(Optional.empty(), Optional.empty());
    }
}
