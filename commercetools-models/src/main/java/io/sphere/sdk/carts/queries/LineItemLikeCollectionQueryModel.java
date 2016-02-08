package io.sphere.sdk.carts.queries;

import io.sphere.sdk.queries.*;

/**
 * Query predicate build for line items.
 * @param <T> query context
 */
public interface LineItemLikeCollectionQueryModel<T> extends QueryModel<T>, CollectionQueryModel<T> {
    @Override
    QueryPredicate<T> isEmpty();

    @Override
    QueryPredicate<T> isNotEmpty();

    StringQueryModel<T> id();

    LongQuerySortingModel<T> quantity();

    LocalizedStringQueryModel<T> name();

    ItemStateCollectionQueryModel<T> state();

    DiscountedLineItemPriceForQuantityQueryModel<T> discountedPricePerQuantity();
}
