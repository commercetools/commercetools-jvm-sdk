package io.sphere.sdk.products.queries;

import io.sphere.sdk.categories.Category;
import io.sphere.sdk.queries.*;

import javax.annotation.Nullable;
import java.util.function.Function;

class ProductDataQueryModelBaseImpl<T> extends ResourceQueryModelImpl<T> implements ProductDataQueryModelBase<T> {

    @Override
    public QueryPredicate<T> where(final QueryPredicate<PartialProductDataQueryModel> embeddedPredicate) {
        return embedPredicate(embeddedPredicate);
    }

    @Override
    public QueryPredicate<T> where(final Function<PartialProductDataQueryModel, QueryPredicate<PartialProductDataQueryModel>> embeddedPredicate) {
        return where(embeddedPredicate.apply(PartialProductDataQueryModel.of()));
    }

    ProductDataQueryModelBaseImpl(@Nullable final QueryModel<T> parent, @Nullable final String pathSegment) {
        super(parent, pathSegment);
    }

    @Override
    public LocalizedStringQuerySortingModel<T> name() {
        return localizedStringQuerySortingModel("name");
    }

    @Override
    public LocalizedStringQueryModel<T> description() {
        return localizedStringQuerySortingModel("description");
    }

    @Override
    public LocalizedStringQuerySortingModel<T> slug() {
        return localizedStringQuerySortingModel("slug");
    }

    @Override
    public ProductAllVariantsQueryModel<T> allVariants() {
        return new ProductAllVariantsQueryModelImpl<>(this);
    }

    @Override
    public ProductVariantQueryModel<T> masterVariant() {
        return ProductVariantQueryModel.of(this, "masterVariant");
    }

    @Override
    public ProductVariantQueryModel<T> variants() {
        return ProductVariantQueryModel.of(this, "variants");
    }

    @Override
    public LocalizedStringQuerySortingModel<T> metaTitle() {
        return localizedStringQuerySortingModel("metaTitle");
    }

    @Override
    public LocalizedStringQuerySortingModel<T> metaKeywords() {
        return localizedStringQuerySortingModel("metaKeywords");
    }

    @Override
    public LocalizedStringQuerySortingModel<T> metaDescription() {
        return localizedStringQuerySortingModel("metaDescription");
    }

    @Override
    public ReferenceCollectionQueryModel<T, Category> categories() {
        return referenceCollectionModel("categories");
    }

    @Override
    public CategoryOrderHintsQueryModel<T> categoryOrderHints() {
        return new CategoryOrderHintsQueryModelImpl<>(this, "categoryOrderHints");
    }
}

