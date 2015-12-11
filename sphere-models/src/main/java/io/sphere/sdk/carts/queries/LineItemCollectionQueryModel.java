package io.sphere.sdk.carts.queries;

import io.sphere.sdk.channels.Channel;
import io.sphere.sdk.products.queries.PriceQueryModel;
import io.sphere.sdk.products.queries.ProductVariantQueryModel;
import io.sphere.sdk.queries.*;
import io.sphere.sdk.types.queries.WithCustomQueryModel;

/**
 * Query predicate build for line items.
 * @param <T> query context
 */
public interface LineItemCollectionQueryModel<T> extends LineItemLikeCollectionQueryModel<T>, WithCustomQueryModel<T> {
    ReferenceOptionalQueryModel<T, Channel> supplyChannel();

    ReferenceOptionalQueryModel<T, Channel> distributionChannel();

    ProductVariantQueryModel<T> variant();

    PriceQueryModel<T> price();

    @Override
    DiscountedLineItemPriceForQuantityQueryModel<T> discountedPricePerQuantity();

    @Override
    StringQueryModel<T> id();

    StringQueryModel<T> productId();

    @Override
    QueryPredicate<T> isEmpty();

    @Override
    QueryPredicate<T> isNotEmpty();

    @Override
    LocalizedStringQueryModel<T> name();

    @Override
    LongQuerySortingModel<T> quantity();

    @Override
    ItemStateCollectionQueryModel<T> state();
}
