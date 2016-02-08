package io.sphere.sdk.customobjects.queries;

import io.sphere.sdk.customobjects.CustomObject;
import io.sphere.sdk.queries.ResourceQueryModel;
import io.sphere.sdk.queries.StringQuerySortingModel;

/**
 * <p>Meta model to explore for which fields can be queried in a CustomObject.</p>
 *
 *
 * <p>Example:</p>
 * {@include.example io.sphere.sdk.customobjects.queries.CustomObjectQueryTest#queryWithClass()}
 *
 * @param <T> The type of the value of the custom object.
 * @see CustomObject
 */
public interface CustomObjectQueryModel<T extends CustomObject<?>> extends ResourceQueryModel<T> {
    StringQuerySortingModel<T> container();

    StringQuerySortingModel<T> key();

    static <T extends CustomObject<?>> CustomObjectQueryModel<T> of() {
        return new CustomObjectQueryModelImpl<>(null, null);
    }
}
