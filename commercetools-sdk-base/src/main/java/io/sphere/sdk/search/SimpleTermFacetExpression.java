package io.sphere.sdk.search;

import javax.annotation.Nullable;

final class SimpleTermFacetExpression<T> extends SimpleFacetExpression<T> implements TermFacetExpression<T> {

    SimpleTermFacetExpression(final String sphereFacetExpression) {
        super(sphereFacetExpression);
    }

    @Nullable
    @Override
    public String value() {
        return null;
    }

    @Override
    protected String pattern() {
        return "^(?<attribute>[^\\s]*)( as (?<alias>.*))?$";
    }
}