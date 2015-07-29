package io.sphere.sdk.products.queries;

import io.sphere.sdk.categories.Category;
import io.sphere.sdk.queries.*;

import javax.annotation.Nullable;
import java.util.function.Function;

class ProductDataQueryModelBase<T> extends DefaultModelQueryModelImpl<T> {

    public QueryPredicate<T> where(final QueryPredicate<PartialProductDataQueryModel> embeddedPredicate) {
        return embedPredicate(embeddedPredicate);
    }

    public QueryPredicate<T> where(final Function<PartialProductDataQueryModel, QueryPredicate<PartialProductDataQueryModel>> embeddedPredicate) {
        return where(embeddedPredicate.apply(PartialProductDataQueryModel.of()));
    }

    ProductDataQueryModelBase(@Nullable final QueryModel<T> parent, @Nullable final String pathSegment) {
        super(parent, pathSegment);
    }

    public LocalizedStringsQuerySortingModel<T> name() {
        return localizedStringsQuerySortingModel("name");
    }

    public LocalizedStringsQueryModel<T> description() {
        return localizedStringsQuerySortingModel("description");
    }

    public LocalizedStringsQuerySortingModel<T> slug() {
        return localizedStringsQuerySortingModel("slug");
    }

    public ProductAllVariantsQueryModel<T> allVariants() {
        return new ProductAllVariantsQueryModel<>(this);
    }

    public ProductVariantQueryModel<T> masterVariant() {
        return new ProductVariantQueryModel<>(this, "masterVariant");
    }

    public ProductVariantQueryModel<T> variants() {
        return new ProductVariantQueryModel<>(this, "variants");
    }

    public LocalizedStringsQuerySortingModel<T> metaTitle() {
        return localizedStringsQuerySortingModel("metaTitle");
    }

    public LocalizedStringsQuerySortingModel<T> metaKeywords() {
        return localizedStringsQuerySortingModel("metaKeywords");
    }

    public LocalizedStringsQuerySortingModel<T> metaDescription() {
        return localizedStringsQuerySortingModel("metaDescription");
    }

    public ReferenceCollectionQueryModel<T, Category> categories() {
        return new ReferenceCollectionQueryModel<>(this, "categories");
    }
}

