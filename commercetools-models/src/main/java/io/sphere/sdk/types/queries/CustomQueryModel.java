package io.sphere.sdk.types.queries;

import io.sphere.sdk.queries.QueryModel;
import io.sphere.sdk.queries.ReferenceQueryModel;
import io.sphere.sdk.types.Type;

import javax.annotation.Nullable;

public interface CustomQueryModel<T> {
    ReferenceQueryModel<T, Type> type();

    FieldsQueryModel<T> fields();

    static <T> CustomQueryModel<T> of(final QueryModel<T> parent, @Nullable final String pathSegment) {
        return new CustomQueryModelImpl<>(parent, pathSegment);
    }
}
