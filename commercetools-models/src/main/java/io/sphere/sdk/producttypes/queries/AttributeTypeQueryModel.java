package io.sphere.sdk.producttypes.queries;

import io.sphere.sdk.queries.StringQueryModel;

public interface AttributeTypeQueryModel<T> {
    StringQueryModel<T> name();

    AttributeTypeQueryModel<T> type();
}
