package io.sphere.sdk.queries;

final class SimpleQueryPredicate<T> extends QueryPredicateBase<T> {
    private final String sphereQuery;

    public SimpleQueryPredicate(final String sphereQuery) {
        this.sphereQuery = sphereQuery;
    }

    @Override
    public String toSphereQuery() {
        return sphereQuery;
    }
}
