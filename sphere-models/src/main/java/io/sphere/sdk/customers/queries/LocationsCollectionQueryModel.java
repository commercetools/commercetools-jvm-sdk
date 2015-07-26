package io.sphere.sdk.customers.queries;

import io.sphere.sdk.queries.*;

import java.util.function.Function;

public class LocationsCollectionQueryModel<T> extends QueryModelImpl<T> {
    public LocationsCollectionQueryModel(final QueryModel<T> parent, final String pathSegment) {
        super(parent, pathSegment);
    }

    public CountryQueryModel<T> country() {
        return new CountryQueryModel<T>(this, "country");
    }

    public StringQueryModel<T> state() {
        return stringModel("state");
    }

    public QueryPredicate<T> where(final QueryPredicate<PartialLocationsCollectionQueryModel> embeddedPredicate) {
        return new EmbeddedQueryPredicate<>(this, embeddedPredicate);
    }

    public QueryPredicate<T> where(final Function<PartialLocationsCollectionQueryModel, QueryPredicate<PartialLocationsCollectionQueryModel>> embeddedPredicate) {
        return where(embeddedPredicate.apply(PartialLocationsCollectionQueryModel.of()));
    }
}
