package io.sphere.sdk.products.queries;

import io.sphere.sdk.categories.Category;
import io.sphere.sdk.queries.*;

import java.util.Optional;

class ProductDataQueryModelBase<M> extends DefaultModelQueryModelImpl<M> {

    ProductDataQueryModelBase(Optional<? extends QueryModel<M>> parent, Optional<String> pathSegment) {
        super(parent, pathSegment);
    }

    public LocalizedStringsQuerySortingModel<M> name() {
        return LocalizedStringsQuerySortingModel.of(this, "name");
    }

    public LocalizedStringsQueryModel<M> description() {
        return LocalizedStringsQuerySortingModel.of(this, "description");
    }

    public LocalizedStringsQuerySortingModel<M> slug() {
        return LocalizedStringsQuerySortingModel.of(this, "slug");
    }

    public ProductVariantQueryModel<M> masterVariant() {
        return new ProductVariantQueryModel<>(Optional.of(this), "masterVariant");
    }

    public ProductVariantQueryModel<M> variants() {
        return new ProductVariantQueryModel<>(Optional.of(this), "variants");
    }

    public LocalizedStringsQuerySortingModel<M> metaTitle() {
        return LocalizedStringsQuerySortingModel.of(this, "metaTitle");
    }

    public LocalizedStringsQuerySortingModel<M> metaKeywords() {
        return LocalizedStringsQuerySortingModel.of(this, "metaKeywords");
    }

    public LocalizedStringsQuerySortingModel<M> metaDescription() {
        return LocalizedStringsQuerySortingModel.of(this, "metaDescription");
    }

    public ReferenceListQueryModel<M, Category> categories() {
        return new ReferenceListQueryModel<>(Optional.of(this), "categories");
    }
}

