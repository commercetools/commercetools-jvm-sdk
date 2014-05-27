package io.sphere.sdk.queries;

import com.google.common.base.Optional;

public class QueryModelImpl<T> implements QueryModel<T> {
    private final Optional<? extends QueryModel<T>> parent;
    private final Optional<String> pathSegment;

    protected QueryModelImpl(final Optional<? extends QueryModel<T>> parent, final String pathSegment) {
        this(parent, Optional.fromNullable(pathSegment));
    }

    protected QueryModelImpl(final Optional<? extends QueryModel<T>> parent, final Optional<String> pathSegment) {
        this.parent = parent;
        this.pathSegment = pathSegment;
    }

    //for testing
    QueryModelImpl<T> appended(final String pathSegment) {
        return new QueryModelImpl<T>(Optional.of(this), pathSegment) ;
    }

    @Override
    public Optional<String> getPathSegment() {
        return pathSegment;
    }

    @Override
    public Optional<? extends QueryModel<T>> getParent() {
        return parent;
    }

    @Override
    public String toString() {
        return "QueryModelImpl{" +
                "parent=" + parent +
                ", pathSegment=" + pathSegment +
                '}';
    }
}
