package io.sphere.sdk.queries;

class SimplePredicate<T> extends PredicateBase<T> {
    private final String sphereQuery;

    public SimplePredicate(final String sphereQuery) {
        this.sphereQuery = sphereQuery;
    }

    @Override
    public String toSphereQuery() {
        return sphereQuery;
    }
}
