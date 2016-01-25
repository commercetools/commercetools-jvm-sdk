package io.sphere.sdk.producttypes.queries;

import io.sphere.sdk.queries.StringQueryModel;

public interface AttributeDefinitionQueryModel<T> {
    StringQueryModel<T> name();

    AttributeTypeQueryModel<T> type();
}
