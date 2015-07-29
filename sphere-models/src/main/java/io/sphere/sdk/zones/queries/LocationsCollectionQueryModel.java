package io.sphere.sdk.zones.queries;

import io.sphere.sdk.queries.CountryQueryModel;
import io.sphere.sdk.queries.QueryPredicate;
import io.sphere.sdk.queries.StringQueryModel;

import java.util.function.Function;

public interface LocationsCollectionQueryModel<T> {
    CountryQueryModel<T> country();

    StringQueryModel<T> state();

    QueryPredicate<T> where(QueryPredicate<PartialLocationsCollectionQueryModel> embeddedPredicate);

    QueryPredicate<T> where(Function<PartialLocationsCollectionQueryModel, QueryPredicate<PartialLocationsCollectionQueryModel>> embeddedPredicate);
}
