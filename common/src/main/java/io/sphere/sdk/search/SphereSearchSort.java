package io.sphere.sdk.search;

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
        if (model.getParent().isPresent()) {
            final String beginning = renderPath(model.getParent().get());

            return beginning +
                    (model.getPathSegment().isPresent() ?
                            (beginning.isEmpty() ? "" : ".") + model.getPathSegment().get() : "");
        } else {
            return model.getPathSegment().orElse("");
        }
    }
}