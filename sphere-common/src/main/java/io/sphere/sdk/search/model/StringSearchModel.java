package io.sphere.sdk.search.model;

import javax.annotation.Nullable;

public class StringSearchModel<T> extends TermModelImpl<T, String>{

    public StringSearchModel(@Nullable final SearchModel<T> parent, @Nullable final String pathSegment) {
        super(parent, pathSegment, TypeSerializer.ofString().getSerializer());
    }
}