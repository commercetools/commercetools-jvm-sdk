package io.sphere.sdk.search;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

abstract class SimpleFacetExpression<T> extends SimpleBaseExpression implements FacetExpression<T> {
    private final String sphereFacetExpression;

    SimpleFacetExpression(final String sphereFacetExpression) {
        this.sphereFacetExpression = sphereFacetExpression;
    }

    @Override
    public String expression() {
        return sphereFacetExpression;
    }

    @Override
    public String resultPath() {
        final Pattern pattern = Pattern.compile(".* as ([\\w]*)");
        final Matcher matcher = pattern.matcher(expression());
        return matcher.find() ? matcher.group(1) : attributePath();
    }
}