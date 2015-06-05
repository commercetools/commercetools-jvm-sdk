package io.sphere.sdk.customobjects.queries;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import io.sphere.sdk.customobjects.CustomObject;
import io.sphere.sdk.queries.PagedQueryResult;
import io.sphere.sdk.queries.UltraQueryDsl;

public interface CustomObjectQuery<T> extends UltraQueryDsl<CustomObject<T>, CustomObjectQuery<T>, CustomObjectQueryModel<CustomObject<T>>, Void> {
    static TypeReference<PagedQueryResult<CustomObject<JsonNode>>> resultTypeReference() {
        return new TypeReference<PagedQueryResult<CustomObject<JsonNode>>>(){
            @Override
            public String toString() {
                return "TypeReference<PagedQueryResult<CustomObject<JsonNode>>>";
            }
        };
    }

    static <T> CustomObjectQuery<T> of(final TypeReference<PagedQueryResult<CustomObject<T>>> resultTypeReference) {
        return new CustomObjectQueryImpl<>(resultTypeReference);
    }

    static CustomObjectQuery<JsonNode> of() {
        return of(resultTypeReference());
    }

    default CustomObjectQuery<T> byContainer(final String container) {
        return withPredicate(CustomObjectQueryModel.<T>of().container().is(container));
    }
}
