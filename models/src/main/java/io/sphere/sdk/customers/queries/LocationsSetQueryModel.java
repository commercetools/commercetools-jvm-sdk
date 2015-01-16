package io.sphere.sdk.customers.queries;

import io.sphere.sdk.queries.CountryQueryModel;
import io.sphere.sdk.queries.QueryModel;
import io.sphere.sdk.queries.QueryModelImpl;

import java.util.Optional;

public class LocationsSetQueryModel<T> extends QueryModelImpl<T> {
    public LocationsSetQueryModel(final Optional<? extends QueryModel<T>> parent, final Optional<String> pathSegment) {
        super(parent, pathSegment);
    }

    public LocationsSetQueryModel(final Optional<? extends QueryModel<T>> parent, final String pathSegment) {
        this(parent, Optional.of(pathSegment));
    }

    public CountryQueryModel<T> country() {
        return new CountryQueryModel<T>(Optional.of(this), Optional.of("country"));
    }
}
