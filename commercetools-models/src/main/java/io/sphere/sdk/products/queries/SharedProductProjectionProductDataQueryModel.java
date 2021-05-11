package io.sphere.sdk.products.queries;

import io.sphere.sdk.categories.Category;
import io.sphere.sdk.queries.LocalizedStringQueryModel;
import io.sphere.sdk.queries.LocalizedStringQuerySortingModel;
import io.sphere.sdk.queries.ReferenceCollectionQueryModel;
import io.sphere.sdk.queries.StringQuerySortingModel;

/**
 * internal interface for queryable fields which are in {@link io.sphere.sdk.products.ProductProjection} AND {@link io.sphere.sdk.products.ProductData}.
 *
 * @param <T> context type
 */
interface SharedProductProjectionProductDataQueryModel<T> {
    LocalizedStringQuerySortingModel<T> name();

    LocalizedStringQueryModel<T> description();

    LocalizedStringQuerySortingModel<T> slug();

    ProductAllVariantsQueryModel<T> allVariants();

    ProductVariantQueryModel<T> masterVariant();

    ProductVariantQueryModel<T> variants();

    LocalizedStringQuerySortingModel<T> metaTitle();

    LocalizedStringQuerySortingModel<T> metaKeywords();

    LocalizedStringQuerySortingModel<T> metaDescription();

    ReferenceCollectionQueryModel<T, Category> categories();

    CategoryOrderHintsQueryModel<T> categoryOrderHints();

    StringQuerySortingModel<T> key();
}
