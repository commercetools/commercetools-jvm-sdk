package io.sphere.sdk.queries;

public class SphereSort<T> implements Sort<T> {
    private final QueryModel<T> path;
    private final SortDirection direction;

    protected SphereSort(QueryModel<T> path, SortDirection direction) {
        this.path = path;
        this.direction = direction;
    }

    public String toSphereSort() {
        return renderPath(path) + " " + direction.toString().toLowerCase();
    }

    private String renderPath(final QueryModel<T> model) {
        if (model.getParent().isPresent()) {
            final String beginning = renderPath(model.getParent().get());

            return beginning +
                    (model.getPathSegment().isPresent() ?
                            (beginning.isEmpty() ? "" : ".") + model.getPathSegment().get() : "");
        } else {
            return model.getPathSegment().orElse("");
        }
    }

    @Override
    public String toString() {
        return "SphereSort{" +
                "path=" + path +
                ", direction=" + direction +
                '}';
    }
}