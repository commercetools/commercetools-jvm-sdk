package io.sphere.sdk.search;

import io.sphere.sdk.models.Base;

import javax.annotation.Nullable;

public class SearchModelImpl<T> extends Base implements SearchModel<T> {
    @Nullable
    private final SearchModel<T> parent;
    @Nullable
    private final String pathSegment;

    protected SearchModelImpl(@Nullable final SearchModel<T> parent, @Nullable final String pathSegment) {
        this.parent = parent;
        this.pathSegment = pathSegment;
    }

    //for testing
    SearchModelImpl<T> appended(final String pathSegment) {
        return new SearchModelImpl<>(this, pathSegment) ;
    }

    @Override
    public String getPathSegment() {
        return pathSegment;
    }

    @Nullable
    @Override
    public SearchModel<T> getParent() {
        return parent;
    }
}
