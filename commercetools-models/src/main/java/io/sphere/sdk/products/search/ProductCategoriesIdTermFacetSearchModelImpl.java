package io.sphere.sdk.products.search;

import io.sphere.sdk.search.FilteredFacetExpression;
import io.sphere.sdk.search.model.SearchModel;
import io.sphere.sdk.search.model.TermFacetSearchModelImpl;
import io.sphere.sdk.search.model.TypeSerializer;

import java.util.function.Function;

import static java.util.Collections.singletonList;

final class ProductCategoriesIdTermFacetSearchModelImpl<T> extends TermFacetSearchModelImpl<T, String> implements ProductCategoriesIdTermFacetSearchModel<T> {

    public static final TypeSerializer<String> SUBTREE_TYPE_SERIALIZER = TypeSerializer.ofRawFunction(s -> String.format("subtree(\"%s\")", s));

    ProductCategoriesIdTermFacetSearchModelImpl(final SearchModel<T> searchModel, final Function<String, String> typeSerializer) {
        super(searchModel, typeSerializer);
    }

    ProductCategoriesIdTermFacetSearchModelImpl(final SearchModel<T> searchModel, final Function<String, String> typeSerializer, final String alias, final Boolean isCountingProducts) {
        super(searchModel, typeSerializer, alias, isCountingProducts);
    }

    @Override
    public FilteredFacetExpression<T> onlyTermSubtree(final String value) {
        return onlyTermSubtree(singletonList(value));
    }

    @Override
    public FilteredFacetExpression<T> onlyTermSubtree(final Iterable<String> values) {
        return new ProductCategoriesIdTermFacetSearchModelImpl<>(searchModel, SUBTREE_TYPE_SERIALIZER, alias, isCountingProducts).onlyTerm(values);
    }

    @Override
    public ProductCategoriesIdTermFacetSearchModel<T> withAlias(final String alias) {
        return new ProductCategoriesIdTermFacetSearchModelImpl<>(searchModel, typeSerializer, alias, isCountingProducts);
    }

    @Override
    public ProductCategoriesIdTermFacetSearchModel<T> withCountingProducts(final Boolean isCountingProducts) {
        return new ProductCategoriesIdTermFacetSearchModelImpl<>(searchModel, typeSerializer, alias, isCountingProducts);
    }
}
