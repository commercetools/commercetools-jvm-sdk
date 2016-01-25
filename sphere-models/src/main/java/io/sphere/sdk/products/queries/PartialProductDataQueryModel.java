package io.sphere.sdk.products.queries;

import io.sphere.sdk.categories.Category;
import io.sphere.sdk.queries.*;

import java.util.function.Function;

public interface PartialProductDataQueryModel {
    ReferenceCollectionQueryModel<PartialProductDataQueryModel, Category> categories();

    LocalizedStringQueryModel<PartialProductDataQueryModel> description();

    ProductVariantQueryModel<PartialProductDataQueryModel> masterVariant();

    LocalizedStringQuerySortingModel<PartialProductDataQueryModel> metaDescription();

    LocalizedStringQuerySortingModel<PartialProductDataQueryModel> metaKeywords();

    LocalizedStringQuerySortingModel<PartialProductDataQueryModel> metaTitle();

    LocalizedStringQuerySortingModel<PartialProductDataQueryModel> name();

    LocalizedStringQuerySortingModel<PartialProductDataQueryModel> slug();

    ProductVariantQueryModel<PartialProductDataQueryModel> variants();

    QueryPredicate<PartialProductDataQueryModel> where(Function<PartialProductDataQueryModel, QueryPredicate<PartialProductDataQueryModel>> embeddedPredicate);

    QueryPredicate<PartialProductDataQueryModel> where(QueryPredicate<PartialProductDataQueryModel> embeddedPredicate);

    static PartialProductDataQueryModel of() {
        return new PartialProductDataQueryModelImpl(null, null);
    }
}
