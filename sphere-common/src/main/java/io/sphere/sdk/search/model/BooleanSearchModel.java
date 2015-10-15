package io.sphere.sdk.search.model;

import javax.annotation.Nullable;

public class BooleanSearchModel<T> extends TermModelImpl<T, Boolean> {

    public BooleanSearchModel(@Nullable final SearchModel<T> parent, @Nullable final String pathSegment) {
        super(parent, pathSegment, TypeSerializer.ofBoolean());
    }
}
