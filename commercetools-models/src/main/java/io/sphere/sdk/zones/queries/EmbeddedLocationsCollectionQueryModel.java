package io.sphere.sdk.zones.queries;

import io.sphere.sdk.queries.CountryQueryModel;
import io.sphere.sdk.queries.StringQueryModel;

public interface EmbeddedLocationsCollectionQueryModel extends SharedLocationsCollectionQueryModel<EmbeddedLocationsCollectionQueryModel> {
    @Override
    CountryQueryModel<EmbeddedLocationsCollectionQueryModel> country();

    @Override
    StringQueryModel<EmbeddedLocationsCollectionQueryModel> state();
}
