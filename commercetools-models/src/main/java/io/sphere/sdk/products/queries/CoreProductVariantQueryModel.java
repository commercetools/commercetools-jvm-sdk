package io.sphere.sdk.products.queries;

import io.sphere.sdk.queries.StringQueryModel;

/**
 * The queryable fields {@link io.sphere.sdk.products.ProductVariant}s support, here without {@code where}
 * @param <T> context
 */
interface CoreProductVariantQueryModel<T> {
    StringQueryModel<T> sku();

    StringQueryModel<T> key();

    PriceCollectionQueryModel<T> prices();
}
