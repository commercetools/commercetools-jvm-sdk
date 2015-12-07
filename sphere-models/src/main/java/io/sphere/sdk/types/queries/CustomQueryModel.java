package io.sphere.sdk.types.queries;

import io.sphere.sdk.queries.ReferenceQueryModel;
import io.sphere.sdk.types.Type;

public interface CustomQueryModel<T> {
    ReferenceQueryModel<T, Type> type();

    FieldsQueryModel<T> fields();
}
