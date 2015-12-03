package io.sphere.sdk.carts.queries;

import io.sphere.sdk.carts.Cart;
import io.sphere.sdk.channels.Channel;
import io.sphere.sdk.products.queries.PriceQueryModel;
import io.sphere.sdk.products.queries.ProductVariantQueryModel;
import io.sphere.sdk.queries.*;

/**
 * Query predicate build for line items.
 * @param <T> query context
 */
public interface LineItemCollectionQueryModel<T> extends QueryModel<T>, CollectionQueryModel<T> {
    ReferenceOptionalQueryModel<T, Channel> supplyChannel();

    ReferenceOptionalQueryModel<T, Channel> distributionChannel();

    @Override
    QueryPredicate<T> isEmpty();

    @Override
    QueryPredicate<T> isNotEmpty();

    StringQueryModel<T> id();

    LongQuerySortingModel<T> quantity();

    LocalizedStringQueryModel<T> name();

    ProductVariantQueryModel<T> variant();

    PriceQueryModel<T> price();
}
