package io.sphere.sdk.customobjects.queries;

import io.sphere.sdk.customobjects.CustomObject;
import io.sphere.sdk.queries.RootJsonQueryModel;
import io.sphere.sdk.queries.ResourceQueryModel;
import io.sphere.sdk.queries.StringQuerySortingModel;

/**
 * <p>Meta model to explore for which fields can be queried in a CustomObject.</p>
 *
 *
 * <p>Example:</p>
 * {@include.example io.sphere.sdk.customobjects.queries.CustomObjectQueryIntegrationTest#queryWithClass()}
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

    /**
     * Query model for {@link CustomObject#getValue()}.
     *
     * <p>Example for custom objects which contain nested objects as value:</p>
     * {@include.example io.sphere.sdk.customobjects.queries.CustomObjectQueryIntegrationTest#demoQueryByNestedValue()}
     * <p>Example for custom objects which contain just a scalar value like a String or a an Integer:</p>
     * {@include.example io.sphere.sdk.customobjects.queries.CustomObjectQueryIntegrationTest#demoQueryByFlatValue()}
     *
     * @return query model
     */
    RootJsonQueryModel<T> value();
}
