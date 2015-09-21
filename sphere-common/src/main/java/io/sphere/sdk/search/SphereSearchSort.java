package io.sphere.sdk.search;

import static io.sphere.sdk.utils.IterableUtils.toStream;
import static java.util.stream.Collectors.joining;

public class SphereSearchSort<T> extends SearchSortBase<T> {
    private final SearchModel<T> searchModel;
    private final SearchSortDirection direction;

    protected SphereSearchSort(final SearchModel<T> searchModel, final SearchSortDirection direction) {
        this.searchModel = searchModel;
        this.direction = direction;
    }

    public String toSphereSort() {
        return renderPath() + " " + directionToString();
    }

    private String directionToString() {
        return direction.toString().toLowerCase().replace("_", ".");
    }

    private String renderPath() {
        return toStream(searchModel.buildPath()).collect(joining("."));
    }
}