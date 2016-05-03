package io.sphere.sdk.queries;

import javax.annotation.Nullable;

final class EnumQueryModelImpl<T> extends QueryModelImpl<T> implements EnumQueryModel<T> {
    public EnumQueryModelImpl(@Nullable final QueryModel<T> parent, @Nullable final String pathSegment) {
        super(parent, pathSegment);
    }

    @Override
    public StringQueryModel<T> key() {
        return stringModel("key");
    }

    @Override
    public StringQueryModel<T> label() {
        return stringModel("label");
    }
}
