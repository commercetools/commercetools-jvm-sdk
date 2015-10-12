package io.sphere.sdk.search;

final class SimpleFilteredFacetExpression<T> extends SimpleFacetExpression<T> implements FilteredFacetExpression<T> {

    SimpleFilteredFacetExpression(final String sphereFacetExpression) {
        super(sphereFacetExpression);
    }
}