package io.sphere.sdk.customobjects.queries;

import com.fasterxml.jackson.core.type.TypeReference;
import io.sphere.sdk.customobjects.CustomObject;
import io.sphere.sdk.queries.DefaultModelQuery;
import io.sphere.sdk.queries.PagedQueryResult;
import io.sphere.sdk.queries.QueryDsl;

public class CustomObjectQuery<T> extends DefaultModelQuery<CustomObject<T>> {
    private CustomObjectQuery(final TypeReference<PagedQueryResult<CustomObject<T>>> resultTypeReference) {
        super(CustomObjectsEndpoint.PATH, resultTypeReference);
    }

    public static <T> CustomObjectQueryModel<T> model() {
        return CustomObjectQueryModel.get();
    }

    public static <T> CustomObjectQuery<T> of(final TypeReference<PagedQueryResult<CustomObject<T>>> resultTypeReference) {
        return new CustomObjectQuery<>(resultTypeReference);
    }

    public QueryDsl<CustomObject<T>> byContainer(final String container) {
        return withPredicate(CustomObjectQuery.<T>model().container().is(container));
    }
}
