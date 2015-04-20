package io.sphere.sdk.search;

import static io.sphere.sdk.utils.IterableUtils.toStream;
import static java.util.stream.Collectors.joining;

public class SphereSearchSort<T> extends SearchSortBase<T> {
    private final SearchModel<T> path;
    private final SearchSortDirection direction;

    protected SphereSearchSort(SearchModel<T> path, SearchSortDirection direction) {
        this.path = path;
        this.direction = direction;
    }

    public String toSphereSort() {
        return renderPath(path) + " " + directionToString();
    }

    private String directionToString() {
        return direction.toString().toLowerCase().replace("_", ".");
    }

    private String renderPath(final SearchModel<T> model) {
        return toStream(model.buildPath()).collect(joining("."));
    }
}