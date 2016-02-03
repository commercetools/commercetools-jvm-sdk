package io.sphere.sdk.search.model;

import javax.annotation.Nullable;

public final class BooleanSearchModel<T> extends TermModelImpl<T, Boolean> {

    BooleanSearchModel(@Nullable final SearchModel<T> parent, @Nullable final String pathSegment) {
        super(parent, pathSegment, TypeSerializer.ofBoolean());
    }
}
