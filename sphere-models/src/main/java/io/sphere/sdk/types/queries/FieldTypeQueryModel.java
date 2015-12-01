package io.sphere.sdk.types.queries;

import io.sphere.sdk.queries.StringQueryModel;

public interface FieldTypeQueryModel<T> {
    StringQueryModel<T> name();
}
