package io.sphere.sdk.queries;

import javax.annotation.Nullable;

final class KeyReferenceQueryModelImpl<T> extends QueryModelImpl<T> implements KeyReferenceQueryModel<T> {

    public KeyReferenceQueryModelImpl(@Nullable final QueryModel<T> parent, @Nullable final String pathSegment) {
        super(parent, pathSegment);
    }
    
    @Override
    public StringQueryModel<T> key() {
        return stringModel("key");
    }

    @Override
    public StringQueryModel<T> typeId() {
        return stringModel("typeId");
    }
}