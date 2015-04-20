package io.sphere.sdk.queries;

public class EmbeddedPredicate<T, V> extends QueryModelPredicate<T> {
    private final Predicate<V> predicate;

    public EmbeddedPredicate(final QueryModel<T> queryModel, final Predicate<V> predicate) {
        super(queryModel);
        this.predicate = predicate;
    }

    @Override
    protected String render() {
        final String sphereQuery = predicate.toSphereQuery();
        return getQueryModel().getParent().isPresent() || getQueryModel().getPathSegment().isPresent() ? "(" + sphereQuery + ")" : sphereQuery;
    }
}
