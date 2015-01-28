package io.sphere.sdk.search;

import java.util.function.Function;

abstract class SearchModelExpression<T, V> extends ExpressionBase<T> {
    private final SearchModel<T> searchModel;
    private final TypeSerializer<V> typeSerializer;

    protected SearchModelExpression(final SearchModel<T> searchModel, final TypeSerializer<V> typeSerializer) {
        this.searchModel = searchModel;
        this.typeSerializer = typeSerializer;
    }

    @Override
    public final String toSphereSearchExpression() {
        return buildQuery(searchModel, render());
    }

    protected abstract String render();

    protected Function<V, String> serializer() {
        return typeSerializer.serializer();
    }
}