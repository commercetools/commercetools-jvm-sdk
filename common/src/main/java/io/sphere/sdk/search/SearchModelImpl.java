package io.sphere.sdk.search;

import io.sphere.sdk.models.Base;

import java.util.Optional;

public class SearchModelImpl<T> extends Base implements SearchModel<T> {
    private final Optional<? extends SearchModel<T>> parent;
    private final Optional<String> pathSegment;

    protected SearchModelImpl(final Optional<? extends SearchModel<T>> parent, final String pathSegment) {
        this(parent, Optional.ofNullable(pathSegment));
    }

    protected SearchModelImpl(final Optional<? extends SearchModel<T>> parent, final Optional<String> pathSegment) {
        this.parent = parent;
        this.pathSegment = pathSegment;
    }

    //for testing
    SearchModelImpl<T> appended(final String pathSegment) {
        return new SearchModelImpl<>(Optional.of(this), pathSegment) ;
    }

    @Override
    public Optional<String> getPathSegment() {
        return pathSegment;
    }

    @Override
    public Optional<? extends SearchModel<T>> getParent() {
        return parent;
    }
}
