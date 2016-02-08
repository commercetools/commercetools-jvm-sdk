package io.sphere.sdk.products.queries;

import io.sphere.sdk.categories.Category;
import io.sphere.sdk.queries.LocalizedStringQueryModel;
import io.sphere.sdk.queries.LocalizedStringQuerySortingModel;
import io.sphere.sdk.queries.QueryModel;
import io.sphere.sdk.queries.ReferenceCollectionQueryModel;

import javax.annotation.Nullable;

class ProductDataQueryModelImpl<T> extends ProductDataQueryModelBaseImpl<T> implements ProductDataQueryModel<T> {

    ProductDataQueryModelImpl(@Nullable final QueryModel<T> parent, @Nullable final String pathSegment) {
        super(parent, pathSegment);
    }

    @Override
    public ReferenceCollectionQueryModel<T, Category> categories() {
        return super.categories();
    }

    @Override
    public LocalizedStringQueryModel<T> description() {
        return super.description();
    }

    @Override
    public ProductAllVariantsQueryModel<T> allVariants() {
        return super.allVariants();
    }

    @Override
    public ProductVariantQueryModel<T> masterVariant() {
        return super.masterVariant();
    }

    @Override
    public LocalizedStringQuerySortingModel<T> metaDescription() {
        return super.metaDescription();
    }

    @Override
    public LocalizedStringQuerySortingModel<T> metaKeywords() {
        return super.metaKeywords();
    }

    @Override
    public LocalizedStringQuerySortingModel<T> metaTitle() {
        return super.metaTitle();
    }

    @Override
    public LocalizedStringQuerySortingModel<T> name() {
        return super.name();
    }

    @Override
    public LocalizedStringQuerySortingModel<T> slug() {
        return super.slug();
    }

    @Override
    public ProductVariantQueryModel<T> variants() {
        return super.variants();
    }

    @Override
    public CategoryOrderHintsQueryModel<T> categoryOrderHints() {
        return super.categoryOrderHints();
    }
}

