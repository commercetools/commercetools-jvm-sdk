package io.sphere.sdk.search;

import io.sphere.sdk.models.Base;

import java.util.function.Function;

import static io.sphere.sdk.utils.IterableUtils.toStream;
import static java.util.stream.Collectors.joining;

abstract class SearchModelExpression<T, V> extends Base {
    private final SearchModel<T> searchModel;
    private final TypeSerializer<V> typeSerializer;

    protected SearchModelExpression(final SearchModel<T> searchModel, final TypeSerializer<V> typeSerializer) {
        this.searchModel = searchModel;
        this.typeSerializer = typeSerializer;
    }

    protected final String path() {
        return toStream(searchModel.buildPath()).collect(joining("."));
    }

    public final String toSphereSearchExpression() {
        return path() + value();
    }

    protected abstract String value();

    protected Function<V, String> serializer() {
        return typeSerializer.serializer();
    }

    @Override
    public String toString() {
        return toSphereSearchExpression();
    }

    @Override
    public final int hashCode() {
        return toSphereSearchExpression().hashCode();
    }
}