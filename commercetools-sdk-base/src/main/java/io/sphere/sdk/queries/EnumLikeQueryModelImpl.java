package io.sphere.sdk.queries;

import javax.annotation.Nullable;

final class EnumLikeQueryModelImpl<T> extends QueryModelImpl<T> implements EnumQueryModel<T>, LocalizedEnumQueryModel<T> {
    public EnumLikeQueryModelImpl(@Nullable final QueryModel<T> parent, @Nullable final String pathSegment) {
        super(parent, pathSegment);
    }

    @Override
    public StringQueryModel<T> key() {
        return stringModel(getParent(), getPathSegment());
    }
}
