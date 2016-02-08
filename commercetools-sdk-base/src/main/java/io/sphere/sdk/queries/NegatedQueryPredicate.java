package io.sphere.sdk.queries;

final class NegatedQueryPredicate<T> extends QueryPredicateBase<T> {
    private final QueryPredicate<T> queryPredicate;

    public NegatedQueryPredicate(final QueryPredicate<T> queryPredicate) {
        this.queryPredicate = queryPredicate;
    }

    @Override
    public String toSphereQuery() {
        return "not(" + queryPredicate.toSphereQuery() + ")";
    }
}
