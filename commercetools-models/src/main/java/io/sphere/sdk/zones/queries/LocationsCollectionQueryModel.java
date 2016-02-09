package io.sphere.sdk.zones.queries;

import io.sphere.sdk.queries.CountryQueryModel;
import io.sphere.sdk.queries.QueryPredicate;
import io.sphere.sdk.queries.StringQueryModel;

import java.util.function.Function;

public interface LocationsCollectionQueryModel<T> extends SharedLocationsCollectionQueryModel<T> {
    @Override
    CountryQueryModel<T> country();

    @Override
    StringQueryModel<T> state();

    QueryPredicate<T> where(QueryPredicate<EmbeddedLocationsCollectionQueryModel> embeddedPredicate);

    QueryPredicate<T> where(Function<EmbeddedLocationsCollectionQueryModel, QueryPredicate<EmbeddedLocationsCollectionQueryModel>> embeddedPredicate);
}
