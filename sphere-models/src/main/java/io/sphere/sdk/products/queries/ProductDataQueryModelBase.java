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
        return LocalizedStringsQuerySortingModel.of(this, "name");
    }

    public LocalizedStringsQueryModel<T> description() {
        return LocalizedStringsQuerySortingModel.of(this, "description");
    }

    public LocalizedStringsQuerySortingModel<T> slug() {
        return LocalizedStringsQuerySortingModel.of(this, "slug");
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
        return LocalizedStringsQuerySortingModel.of(this, "metaTitle");
    }

    public LocalizedStringsQuerySortingModel<T> metaKeywords() {
        return LocalizedStringsQuerySortingModel.of(this, "metaKeywords");
    }

    public LocalizedStringsQuerySortingModel<T> metaDescription() {
        return LocalizedStringsQuerySortingModel.of(this, "metaDescription");
    }

    public ReferenceCollectionQueryModel<T, Category> categories() {
        return new ReferenceCollectionQueryModel<>(this, "categories");
    }
}

