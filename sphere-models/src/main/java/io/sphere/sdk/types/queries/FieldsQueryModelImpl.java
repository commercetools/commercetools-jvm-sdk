package io.sphere.sdk.types.queries;

import io.sphere.sdk.queries.LocalizedStringOptionalQueryModel;
import io.sphere.sdk.queries.QueryModel;
import io.sphere.sdk.queries.QueryModelImpl;
import io.sphere.sdk.queries.StringQueryModel;

final class FieldsQueryModelImpl<T> extends QueryModelImpl<T> implements FieldsQueryModel<T> {
    public FieldsQueryModelImpl(final QueryModel<T> parent, final String pathSegment) {
        super(parent, pathSegment);
    }

    @Override
    public StringQueryModel<T> ofString(final String name) {
        return stringModel(name);
    }

    @Override
    public LocalizedStringOptionalQueryModel<T> ofLocalizedString(final String name) {
        return localizedStringQuerySortingModel(name);
    }
}
