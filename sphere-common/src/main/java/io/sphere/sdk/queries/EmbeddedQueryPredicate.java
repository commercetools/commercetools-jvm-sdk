package io.sphere.sdk.queries;

final class EmbeddedQueryPredicate<T, V> extends QueryModelQueryPredicate<T> {
    private final QueryPredicate<V> predicate;

    public EmbeddedQueryPredicate(final QueryModel<T> queryModel, final QueryPredicate<V> predicate) {
        super(queryModel);
        this.predicate = predicate;
    }

    @Override
    protected String render() {
        final String sphereQuery = predicate.toSphereQuery();
        return getQueryModel().getParent() != null || getQueryModel().getPathSegment() != null ? "(" + sphereQuery + ")" : sphereQuery;
    }
}
