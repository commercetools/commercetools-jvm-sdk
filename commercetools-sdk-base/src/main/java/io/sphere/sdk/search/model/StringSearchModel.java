package io.sphere.sdk.search.model;

import javax.annotation.Nullable;

public final class StringSearchModel<T> extends TermModelImpl<T, String>{

    StringSearchModel(@Nullable final SearchModel<T> parent, @Nullable final String pathSegment) {
        super(parent, pathSegment, TypeSerializer.ofString());
    }
}