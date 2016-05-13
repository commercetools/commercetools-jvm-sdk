package io.sphere.sdk.products.search;

import io.sphere.sdk.search.FilterExpression;
import io.sphere.sdk.search.model.SearchModel;
import io.sphere.sdk.search.model.TermFilterExpression;
import io.sphere.sdk.search.model.TermFilterSearchModelImpl;
import io.sphere.sdk.search.model.TypeSerializer;

import java.util.LinkedList;
import java.util.List;
import java.util.function.Function;

import static java.util.Collections.*;

final class ProductCategoriesIdTermFilterSearchModelImpl<T> extends TermFilterSearchModelImpl<T, String> implements ProductCategoriesIdTermFilterSearchModel<T> {
    public ProductCategoriesIdTermFilterSearchModelImpl(final SearchModel<T> searchModel, final Function<String, String> typeSerializer) {
        super(searchModel, typeSerializer);
    }

    @Override
    public List<FilterExpression<T>> containsAnyIncludingSubtrees(final Iterable<String> categoryIds) {
        return isInSubtreeOrInCategory(categoryIds, emptyList());
    }

    @Override
    public List<FilterExpression<T>> containsAllIncludingSubtrees(final Iterable<String> categoryIds) {
        final List<FilterExpression<T>> result = new LinkedList<>();
        categoryIds.forEach(id -> result.addAll(isInSubtree(id)));
        return unmodifiableList(result);
    }

    @Override
    public List<FilterExpression<T>> isInSubtree(final String categoryId) {
        return containsAnyIncludingSubtrees(singletonList(categoryId));
    }

    @Override
    public List<FilterExpression<T>> isInSubtreeOrInCategory(final Iterable<String> categoryIdsSubtree, final Iterable<String> categoryIdsDirectly) {
        final List<String> list = new LinkedList<>();
        categoryIdsSubtree.forEach(subtreeId -> list.add(String.format("subtree(\"%s\")", subtreeId)));
        categoryIdsDirectly.forEach(id -> list.add(String.format("\"%s\"", id)));
        return singletonList(new TermFilterExpression<>(searchModel, TypeSerializer.ofRawString(), list));
    }
}
