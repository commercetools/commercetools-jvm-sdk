package io.sphere.sdk.queries;

import javax.annotation.Nullable;

final class LocalizedEnumQueryModelImpl<T> extends QueryModelImpl<T> implements LocalizedEnumQueryModel<T> {
    public LocalizedEnumQueryModelImpl(@Nullable final QueryModel<T> parent, @Nullable final String pathSegment) {
        super(parent, pathSegment);
    }

    @Override
    public StringQueryModel<T> key() {
        return stringModel("key");
    }

    @Override
    public LocalizedStringQueryModel<T> label() {
        return localizedStringQuerySortingModel("label");
    }
}
