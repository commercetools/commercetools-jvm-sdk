package io.sphere.sdk.carts.queries;

import io.sphere.sdk.channels.Channel;
import io.sphere.sdk.products.queries.PriceQueryModel;
import io.sphere.sdk.products.queries.ProductVariantQueryModel;
import io.sphere.sdk.queries.*;

/**
 * Query predicate build for line items.
 * @param <T> query context
 */
public interface LineItemCollectionQueryModel<T> extends LineItemLikeCollectionQueryModel<T> {
    ReferenceOptionalQueryModel<T, Channel> supplyChannel();

    ReferenceOptionalQueryModel<T, Channel> distributionChannel();

    ProductVariantQueryModel<T> variant();

    PriceQueryModel<T> price();

    @Override
    DiscountedLineItemPriceForQuantityQueryModel<T> discountedPricePerQuantity();

    @Override
    StringQueryModel<T> id();

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
