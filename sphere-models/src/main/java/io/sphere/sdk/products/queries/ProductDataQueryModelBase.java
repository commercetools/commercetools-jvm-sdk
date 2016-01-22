package io.sphere.sdk.products.queries;

import io.sphere.sdk.categories.Category;
import io.sphere.sdk.queries.*;

import javax.annotation.Nullable;
import java.util.function.Function;

class ProductDataQueryModelBase<T> extends ResourceQueryModelImpl<T> {

    public QueryPredicate<T> where(final QueryPredicate<PartialProductDataQueryModel> embeddedPredicate) {
        return embedPredicate(embeddedPredicate);
    }

    public QueryPredicate<T> where(final Function<PartialProductDataQueryModel, QueryPredicate<PartialProductDataQueryModel>> embeddedPredicate) {
        return where(embeddedPredicate.apply(PartialProductDataQueryModel.of()));
    }

    ProductDataQueryModelBase(@Nullable final QueryModel<T> parent, @Nullable final String pathSegment) {
        super(parent, pathSegment);
    }

    public LocalizedStringQuerySortingModel<T> name() {
        return localizedStringQuerySortingModel("name");
    }

    public LocalizedStringQueryModel<T> description() {
        return localizedStringQuerySortingModel("description");
    }

    public LocalizedStringQuerySortingModel<T> slug() {
        return localizedStringQuerySortingModel("slug");
    }

    public ProductAllVariantsQueryModel<T> allVariants() {
        return new ProductAllVariantsQueryModelImpl<>(this);
    }

    public ProductVariantQueryModel<T> masterVariant() {
        return ProductVariantQueryModel.of(this, "masterVariant");
    }

    public ProductVariantQueryModel<T> variants() {
        return ProductVariantQueryModel.of(this, "variants");
    }

    public LocalizedStringQuerySortingModel<T> metaTitle() {
        return localizedStringQuerySortingModel("metaTitle");
    }

    public LocalizedStringQuerySortingModel<T> metaKeywords() {
        return localizedStringQuerySortingModel("metaKeywords");
    }

    public LocalizedStringQuerySortingModel<T> metaDescription() {
        return localizedStringQuerySortingModel("metaDescription");
    }

    public ReferenceCollectionQueryModel<T, Category> categories() {
        return referenceCollectionModel("categories");
    }

    public CategoryOrderHintsQueryModel<T> categoryOrderHints() {
        return new CategoryOrderHintsQueryModelImpl<>(this, "categoryOrderHints");
    }
}

