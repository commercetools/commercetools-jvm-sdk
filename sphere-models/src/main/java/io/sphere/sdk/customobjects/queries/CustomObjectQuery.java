package io.sphere.sdk.customobjects.queries;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import io.sphere.sdk.customobjects.CustomObject;
import io.sphere.sdk.queries.DefaultModelQuery;
import io.sphere.sdk.queries.PagedQueryResult;
import io.sphere.sdk.queries.QueryDsl;

public class CustomObjectQuery<T> extends DefaultModelQuery<CustomObject<T>> {

    private static final TypeReference<PagedQueryResult<CustomObject<JsonNode>>> RESULT_TYPE_REFERENCE = new TypeReference<PagedQueryResult<CustomObject<JsonNode>>>() {
        @Override
        public String toString() {
            return "TypeReference<PagedQueryResult<CustomObject<JsonNode>>>";
        }
    };

    private CustomObjectQuery(final TypeReference<PagedQueryResult<CustomObject<T>>> resultTypeReference) {
        super(CustomObjectsEndpoint.PATH, resultTypeReference);
    }

    public static <T> CustomObjectQueryModel<T> model() {
        return CustomObjectQueryModel.get();
    }

    public static <T> CustomObjectQuery<T> of(final TypeReference<PagedQueryResult<CustomObject<T>>> resultTypeReference) {
        return new CustomObjectQuery<>(resultTypeReference);
    }

    public static CustomObjectQuery<JsonNode> of() {
        return of(RESULT_TYPE_REFERENCE);
    }

    public QueryDsl<CustomObject<T>> byContainer(final String container) {
        return withPredicate(CustomObjectQuery.<T>model().container().is(container));
    }
}
