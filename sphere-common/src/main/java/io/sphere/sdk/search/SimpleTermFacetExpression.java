package io.sphere.sdk.search;

final class SimpleTermFacetExpression<T> extends SimpleFacetExpression<T> implements TermFacetExpression<T> {

    SimpleTermFacetExpression(final String sphereFacetExpression) {
        super(sphereFacetExpression);
    }
}