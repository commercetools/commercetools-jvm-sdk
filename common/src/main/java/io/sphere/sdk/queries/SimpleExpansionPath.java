package io.sphere.sdk.queries;

import io.sphere.sdk.models.Base;

final class SimpleExpansionPath<T> extends Base implements ExpansionPath<T> {

    private final String sphereExpansionPathExpression;

    SimpleExpansionPath(final String sphereExpansionPathExpression) {
        this.sphereExpansionPathExpression = sphereExpansionPathExpression;
    }

    @Override
    public String toSphereExpand() {
        return sphereExpansionPathExpression;
    }
}
