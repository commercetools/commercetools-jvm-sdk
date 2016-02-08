package io.sphere.sdk.queries;

import java.util.Optional;

public final class SphereQuerySort<T> extends QuerySortBase<T> {
    private final QueryModel<T> path;
    private final QuerySortDirection direction;

    protected SphereQuerySort(QueryModel<T> path, QuerySortDirection direction) {
        this.path = path;
        this.direction = direction;
    }

    public String toSphereSort() {
        return renderPath(path) + " " + direction.toString().toLowerCase();
    }

    private String renderPath(final QueryModel<T> model) {
        if (model.getParent() != null) {
            final String beginning = renderPath(model.getParent());

            return beginning +
                    (model.getPathSegment() != null ?
                            (beginning.isEmpty() ? "" : ".") + model.getPathSegment() : "");
        } else {
            return Optional.ofNullable(model.getPathSegment()).orElse("");
        }
    }
}