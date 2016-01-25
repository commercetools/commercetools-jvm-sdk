package io.sphere.sdk.products.queries;

import io.sphere.sdk.categories.Category;
import io.sphere.sdk.queries.LocalizedStringQueryModel;
import io.sphere.sdk.queries.LocalizedStringQuerySortingModel;
import io.sphere.sdk.queries.ReferenceCollectionQueryModel;

public interface ProductDataQueryModel<T> extends ProductDataQueryModelBase<T> {
    ReferenceCollectionQueryModel<T, Category> categories();

    LocalizedStringQueryModel<T> description();

    ProductAllVariantsQueryModel<T> allVariants();

    ProductVariantQueryModel<T> masterVariant();

    LocalizedStringQuerySortingModel<T> metaDescription();

    LocalizedStringQuerySortingModel<T> metaKeywords();

    LocalizedStringQuerySortingModel<T> metaTitle();

    LocalizedStringQuerySortingModel<T> name();

    LocalizedStringQuerySortingModel<T> slug();

    ProductVariantQueryModel<T> variants();

    CategoryOrderHintsQueryModel<T> categoryOrderHints();
}
