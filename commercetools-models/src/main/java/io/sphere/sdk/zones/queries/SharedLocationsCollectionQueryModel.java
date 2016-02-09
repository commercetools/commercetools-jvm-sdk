package io.sphere.sdk.zones.queries;

import io.sphere.sdk.queries.CountryQueryModel;
import io.sphere.sdk.queries.StringQueryModel;

interface SharedLocationsCollectionQueryModel<T> {
    CountryQueryModel<T> country();

    StringQueryModel<T> state();
}
