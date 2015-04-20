package io.sphere.sdk.customobjects.queries;

import io.sphere.sdk.customobjects.CustomObject;
import io.sphere.sdk.queries.DefaultModelQueryModelImpl;
import io.sphere.sdk.queries.QueryModel;
import io.sphere.sdk.queries.StringQuerySortingModel;

import java.util.Optional;

/**
 * Metamodel to explore for which fields can be queried in a {@link io.sphere.sdk.customobjects.CustomObject}.
 * @param <T> The type of the value of the custom object.
 */
public class CustomObjectQueryModel<T> extends DefaultModelQueryModelImpl<CustomObject<T>> {

    static <T> CustomObjectQueryModel<T> get() {
        return new CustomObjectQueryModel<>(Optional.<QueryModel<CustomObject<T>>>empty(), Optional.<String>empty());
    }

    private CustomObjectQueryModel(final Optional<? extends QueryModel<CustomObject<T>>> parent, final Optional<String> pathSegment) {
        super(parent, pathSegment);
    }

    public StringQuerySortingModel<CustomObject<T>> container() {
        return new StringQuerySortingModel<>(Optional.of(this), "container");
    }
}
