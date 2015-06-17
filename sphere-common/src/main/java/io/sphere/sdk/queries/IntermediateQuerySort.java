package io.sphere.sdk.queries;

import io.sphere.sdk.models.Base;

import static io.sphere.sdk.queries.QuerySortDirection.*;

/** A sort model to decide the direction. */
public class IntermediateQuerySort<T> extends Base {
    private final QueryModel<T> path;

    IntermediateQuerySort(final QueryModel<T> path) {
        this.path = path;
    }

    public QuerySort<T> asc() {
        return getQuerySort(ASC);
    }

    public QuerySort<T> desc() {
        return getQuerySort(DESC);
    }

    private QuerySort<T> getQuerySort(final QuerySortDirection direction) {
        return new SphereQuerySort<>(path, direction);
    }
}
