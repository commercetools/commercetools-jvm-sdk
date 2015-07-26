package io.sphere.sdk.search;

import io.sphere.sdk.models.Base;

import javax.annotation.Nullable;
import java.util.Optional;
import java.util.function.Function;

import static io.sphere.sdk.utils.IterableUtils.toStream;
import static java.util.stream.Collectors.joining;

abstract class SearchModelExpression<T, V> extends Base {
    private final SearchModel<T> searchModel;
    private final TypeSerializer<V> typeSerializer;
    @Nullable
    protected final String alias;

    protected SearchModelExpression(final SearchModel<T> searchModel, final TypeSerializer<V> typeSerializer, @Nullable final String alias) {
        this.searchModel = searchModel;
        this.typeSerializer = typeSerializer;
        this.alias = alias;
    }

    protected final String buildResultPath() {
        return Optional.ofNullable(alias).orElse(serializedPath());
    }

    protected final String serializedPath() {
        return toStream(searchModel.buildPath()).collect(joining("."));
    }

    protected final String serializedAlias() {
        return Optional.ofNullable(alias).map(a -> " as " + a).orElse("");
    }

    protected abstract String serializedValue();

    public final String toSphereSearchExpression() {
        return serializedPath() + serializedValue() + serializedAlias();
    }

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