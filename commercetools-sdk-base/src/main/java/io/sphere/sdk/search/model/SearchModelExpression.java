package io.sphere.sdk.search.model;

import io.sphere.sdk.models.Base;

import javax.annotation.Nullable;
import java.util.Optional;
import java.util.function.Function;

import static java.util.Objects.requireNonNull;

abstract class SearchModelExpression<T, V> extends Base {
    private final SearchModel<T> searchModel;
    private final Function<V, String> typeSerializer;
    @Nullable
    protected final String alias;
    protected final Boolean isCountingProducts;

    protected SearchModelExpression(final SearchModel<T> searchModel, final Function<V, String> typeSerializer, @Nullable final String alias, final Boolean isCountingProducts) {
        this.searchModel = requireNonNull(searchModel);
        this.typeSerializer = requireNonNull(typeSerializer);
        this.alias = alias;
        this.isCountingProducts = isCountingProducts;
    }

    public final String expression() {
        return searchModel.attributePath() + Optional.ofNullable(value()).orElse("") + Optional.ofNullable(alias).map(a -> " as " + a).orElse("") + (isCountingProducts ? " counting products" : "");
    }

    public final String attributePath() {
        return searchModel.attributePath();
    }

    public final String resultPath() {
        return Optional.ofNullable(alias).orElse(searchModel.attributePath());
    }

    @Nullable
    protected String alias() {
        return alias;
    }

    protected Boolean isCountingProducts() {
        return isCountingProducts;
    }

    @Nullable
    protected abstract String value();

    protected Function<V, String> serializer() {
        return typeSerializer;
    }

    @Override
    public String toString() {
        return expression();
    }

    @Override
    public final int hashCode() {
        return expression().hashCode();
    }
}