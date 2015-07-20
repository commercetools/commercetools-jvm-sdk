package io.sphere.sdk.customers.queries;

import io.sphere.sdk.queries.*;

import java.util.Optional;
import java.util.function.Function;

public class LocationsCollectionQueryModel<T> extends QueryModelImpl<T> {
    public LocationsCollectionQueryModel(final Optional<? extends QueryModel<T>> parent, final Optional<String> pathSegment) {
        super(parent, pathSegment);
    }

    public LocationsCollectionQueryModel(final Optional<? extends QueryModel<T>> parent, final String pathSegment) {
        this(parent, Optional.of(pathSegment));
    }

    public CountryQueryModel<T> country() {
        return new CountryQueryModel<T>(Optional.of(this), Optional.of("country"));
    }

    public StringQueryModel<T> state() {
        return new StringQuerySortingModel<>(Optional.of(this), "state");
    }

    public QueryPredicate<T> where(final QueryPredicate<PartialLocationsCollectionQueryModel> embeddedPredicate) {
        return new EmbeddedQueryPredicate<>(this, embeddedPredicate);
    }

    public QueryPredicate<T> where(final Function<PartialLocationsCollectionQueryModel, QueryPredicate<PartialLocationsCollectionQueryModel>> embeddedPredicate) {
        return where(embeddedPredicate.apply(PartialLocationsCollectionQueryModel.of()));
    }
}
