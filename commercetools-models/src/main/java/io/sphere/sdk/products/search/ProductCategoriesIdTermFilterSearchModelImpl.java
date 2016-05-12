package io.sphere.sdk.products.search;

import io.sphere.sdk.search.FilterExpression;
import io.sphere.sdk.search.model.SearchModel;
import io.sphere.sdk.search.model.TermFilterExpression;
import io.sphere.sdk.search.model.TermFilterSearchModelImpl;
import io.sphere.sdk.search.model.TypeSerializer;

import java.util.LinkedList;
import java.util.List;
import java.util.function.Function;

import static java.util.Collections.emptyList;
import static java.util.Collections.singletonList;

public class ProductCategoriesIdTermFilterSearchModelImpl<T> extends TermFilterSearchModelImpl<T, String> implements ProductCategoriesIdTermFilterSearchModel<T> {
    public ProductCategoriesIdTermFilterSearchModelImpl(final SearchModel<T> searchModel, final Function<String, String> typeSerializer) {
        super(searchModel, typeSerializer);
    }

    @Override
    public List<FilterExpression<T>> containsAnyIncludingSubtrees(final Iterable<String> categoryIds) {
        return isSubtreeRootOrInCategory(categoryIds, emptyList());
    }

    @Override
    public List<FilterExpression<T>> isIncludingSubtree(final String categoryId) {
        return containsAnyIncludingSubtrees(singletonList(categoryId));
    }

    @Override
    public List<FilterExpression<T>> isSubtreeRootOrInCategory(final Iterable<String> categoryIdsSubtree, final Iterable<String> categoryIdsDirectly) {
        final List<String> list = new LinkedList<>();
        categoryIdsSubtree.forEach(subtreeId -> list.add(String.format("subtree(\"%s\")", subtreeId)));
        categoryIdsDirectly.forEach(id -> list.add(String.format("\"%s\"", id)));
        return singletonList(new TermFilterExpression<>(searchModel, TypeSerializer.ofRawString(), list));
    }
}
