package io.sphere.sdk.products.queries;

import io.sphere.sdk.categories.Category;
import io.sphere.sdk.queries.LocalizedStringQueryModel;
import io.sphere.sdk.queries.LocalizedStringQuerySortingModel;
import io.sphere.sdk.queries.QueryPredicate;
import io.sphere.sdk.queries.ReferenceCollectionQueryModel;

import java.util.function.Function;

interface ProductDataQueryModelBase<T> {
    QueryPredicate<T> where(QueryPredicate<PartialProductDataQueryModel> embeddedPredicate);

    QueryPredicate<T> where(Function<PartialProductDataQueryModel, QueryPredicate<PartialProductDataQueryModel>> embeddedPredicate);

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
}
