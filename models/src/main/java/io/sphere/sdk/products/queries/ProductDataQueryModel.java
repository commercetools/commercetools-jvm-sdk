package io.sphere.sdk.products.queries;

import java.util.Optional;

import io.sphere.sdk.categories.Category;
import io.sphere.sdk.queries.*;

public class ProductDataQueryModel<T> extends ProductDataQueryModelBase<T> {

    ProductDataQueryModel(Optional<? extends QueryModel<T>> parent, Optional<String> pathSegment) {
        super(parent, pathSegment);
    }

    @Override
    public ReferenceListQueryModel<T, Category> categories() {
        return super.categories();
    }

    @Override
    public LocalizedStringsQueryModel<T> description() {
        return super.description();
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

