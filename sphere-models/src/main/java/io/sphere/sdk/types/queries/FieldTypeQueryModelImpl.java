package io.sphere.sdk.types.queries;

import io.sphere.sdk.queries.QueryModel;
import io.sphere.sdk.queries.QueryModelImpl;
import io.sphere.sdk.queries.StringQueryModel;

final class FieldTypeQueryModelImpl<T> extends QueryModelImpl<T> implements FieldTypeQueryModel<T> {
    public FieldTypeQueryModelImpl(final QueryModel<T> parent, final String pathSegment) {
        super(parent, pathSegment);
    }

    @Override
    public StringQueryModel<T> name() {
        return stringModel("name");
    }
}
