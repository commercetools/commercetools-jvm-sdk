package io.sphere.sdk.customobjects.queries;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import io.sphere.sdk.customobjects.CustomObject;
import io.sphere.sdk.customobjects.expansion.CustomObjectExpansionModel;
import io.sphere.sdk.queries.PagedQueryResult;
import io.sphere.sdk.queries.MetaModelQueryDsl;

public interface CustomObjectQuery<T> extends MetaModelQueryDsl<CustomObject<T>, CustomObjectQuery<T>, CustomObjectQueryModel<CustomObject<T>>, CustomObjectExpansionModel<CustomObject<T>>> {
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
        return withPredicates(CustomObjectQueryModel.<T>of().container().is(container));
    }
}
