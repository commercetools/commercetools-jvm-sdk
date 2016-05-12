package io.sphere.sdk.products.search;

import io.sphere.sdk.search.FilterExpression;
import io.sphere.sdk.search.model.TermFilterSearchModel;

import java.util.List;

/**
 * Model to build term filters.
 * @param <T> type of the resource
 */
public interface ProductCategoriesIdTermFilterSearchModel<T> extends TermFilterSearchModel<T, String> {
    @Override
    List<FilterExpression<T>> containsAll(final Iterable<String> values);

    @Override
    List<FilterExpression<T>> containsAllAsString(final Iterable<String> values);

    @Override
    List<FilterExpression<T>> containsAny(final Iterable<String> values);

    @Override
    List<FilterExpression<T>> containsAnyAsString(final Iterable<String> values);

    @Override
    List<FilterExpression<T>> is(final String categoryId);

    @Override
    List<FilterExpression<T>> isIn(final Iterable<String> categoryIds);

    List<FilterExpression<T>> containsAnyIncludingSubtrees(final Iterable<String> categoryIds);

    List<FilterExpression<T>> isIncludingSubtree(final String categoryId);

    List<FilterExpression<T>> isSubtreeRootOrInCategory(final Iterable<String> categoryIdsSubtree, final Iterable<String> categoryIdsDirectly);
}
