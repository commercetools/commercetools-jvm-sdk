package io.sphere.sdk.queries;

import io.sphere.sdk.models.Base;

import static io.sphere.sdk.queries.QuerySortDirection.*;

/** A sort model to decide the direction. */
public final class DirectionlessQuerySort<T> extends Base {
    private final QueryModel<T> path;

    DirectionlessQuerySort(final QueryModel<T> path) {
        this.path = path;
    }

    public QuerySort<T> asc() {
        return by(ASC);
    }

    public QuerySort<T> desc() {
        return by(DESC);
    }

    public static <T> DirectionlessQuerySort<T> of(final QueryModel<T> path) {
        return new DirectionlessQuerySort<>(path);
    }

    public QuerySort<T> by(final QuerySortDirection direction) {
        return new SphereQuerySort<>(path, direction);
    }
}
