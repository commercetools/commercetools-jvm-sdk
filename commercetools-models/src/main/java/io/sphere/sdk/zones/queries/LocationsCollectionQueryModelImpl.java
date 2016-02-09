package io.sphere.sdk.zones.queries;

import io.sphere.sdk.queries.*;

import java.util.function.Function;

class LocationsCollectionQueryModelImpl<T> extends QueryModelImpl<T> implements LocationsCollectionQueryModel<T> {
    public LocationsCollectionQueryModelImpl(final QueryModel<T> parent, final String pathSegment) {
        super(parent, pathSegment);
    }

    @Override
    public CountryQueryModel<T> country() {
        return countryQueryModel("country");
    }

    @Override
    public StringQueryModel<T> state() {
        return stringModel("state");
    }

    @Override
    public QueryPredicate<T> where(final QueryPredicate<EmbeddedLocationsCollectionQueryModel> embeddedPredicate) {
        return embedPredicate(embeddedPredicate);
    }

    @Override
    public QueryPredicate<T> where(final Function<EmbeddedLocationsCollectionQueryModel, QueryPredicate<EmbeddedLocationsCollectionQueryModel>> embeddedPredicate) {
        return where(embeddedPredicate.apply(EmbeddedLocationsCollectionQueryModelImpl.of()));
    }
}
