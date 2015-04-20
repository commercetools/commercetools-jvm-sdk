package io.sphere.sdk.search;

import io.sphere.sdk.models.Base;

final class SimpleFilterExpression<T> extends Base implements FilterExpression<T> {
    private final String sphereFilterExpression;

    SimpleFilterExpression(final String sphereFilterExpression) {
        this.sphereFilterExpression = sphereFilterExpression;
    }

    @Override
    public String toSphereFilter() {
        return sphereFilterExpression;
    }
}