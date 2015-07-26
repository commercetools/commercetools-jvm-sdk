package io.sphere.sdk.products.queries;

import io.sphere.sdk.categories.Category;
import io.sphere.sdk.queries.LocalizedStringsQueryModel;
import io.sphere.sdk.queries.LocalizedStringsQuerySortingModel;
import io.sphere.sdk.queries.QueryModel;
import io.sphere.sdk.queries.ReferenceCollectionQueryModel;

import javax.annotation.Nullable;

public class ProductDataQueryModel<T> extends ProductDataQueryModelBase<T> {

    ProductDataQueryModel(@Nullable final QueryModel<T> parent, @Nullable final String pathSegment) {
        super(parent, pathSegment);
    }

    @Override
    public ReferenceCollectionQueryModel<T, Category> categories() {
        return super.categories();
    }

    @Override
    public LocalizedStringsQueryModel<T> description() {
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
    public LocalizedStringsQuerySortingModel<T> metaDescription() {
        return super.metaDescription();
    }

    @Override
    public LocalizedStringsQuerySortingModel<T> metaKeywords() {
        return super.metaKeywords();
    }

    @Override
    public LocalizedStringsQuerySortingModel<T> metaTitle() {
        return super.metaTitle();
    }

    @Override
    public LocalizedStringsQuerySortingModel<T> name() {
        return super.name();
    }

    @Override
    public LocalizedStringsQuerySortingModel<T> slug() {
        return super.slug();
    }

    @Override
    public ProductVariantQueryModel<T> variants() {
        return super.variants();
    }
}

