package io.sphere.sdk.queries;

import io.sphere.sdk.models.Base;

import static io.sphere.sdk.queries.QuerySortDirection.*;

/** A sort model to decide the direction. */
public class DirectionlessQuerySort<T> extends Base {
    private final QueryModel<T> path;

    DirectionlessQuerySort(final QueryModel<T> path) {
        this.path = path;
    }

    public QuerySort<T> asc() {
        return getQuerySort(ASC);
    }

    public QuerySort<T> desc() {
        return getQuerySort(DESC);
    }

    public static <T> DirectionlessQuerySort<T> of(final QueryModel<T> path) {
        return new DirectionlessQuerySort<>(path);
    }

    private QuerySort<T> getQuerySort(final QuerySortDirection direction) {
        return new SphereQuerySort<>(path, direction);
    }
}
