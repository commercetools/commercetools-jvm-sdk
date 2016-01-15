package io.sphere.sdk.customobjects.queries;

import io.sphere.sdk.customobjects.CustomObject;
import io.sphere.sdk.queries.QueryModel;
import io.sphere.sdk.queries.ResourceQueryModelImpl;
import io.sphere.sdk.queries.StringQuerySortingModel;

/**
 * <p>Metamodel to explore for which fields can be queried in a CustomObject.</p>
 *
 *
 * <p>Example:</p>
 * {@include.example io.sphere.sdk.customobjects.queries.CustomObjectQueryTest#queryWithClass()}
 *
 * @param <T> The type of the value of the custom object.
 * @see CustomObject
 */
public class CustomObjectQueryModel<T extends CustomObject<?>> extends ResourceQueryModelImpl<T> {

    public static <T extends CustomObject<?>> CustomObjectQueryModel<T> of() {
        return new CustomObjectQueryModel<>(null, null);
    }

    private CustomObjectQueryModel(final QueryModel<T> parent, final String pathSegment) {
        super(parent, pathSegment);
    }

    public StringQuerySortingModel<T> container() {
        return stringModel("container");
    }

    public StringQuerySortingModel<T> key() {
        return stringModel("key");
    }
}
