package io.sphere.sdk.search;

import javax.annotation.Nullable;
import java.util.Optional;

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
        return Optional.ofNullable(alias()).orElse(attributePath());
    }

    @Nullable
    @Override
    public String alias() {
        return matchExpression(matcher -> matcher.group("alias"));
    }
}