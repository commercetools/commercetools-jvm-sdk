package io.sphere.sdk.queries;

final class SimpleExpansionPath<T> extends ExpansionPathBase<T> {

    private final String sphereExpansionPathExpression;

    SimpleExpansionPath(final String sphereExpansionPathExpression) {
        this.sphereExpansionPathExpression = sphereExpansionPathExpression;
    }

    @Override
    public String toSphereExpand() {
        return sphereExpansionPathExpression;
    }
}
