package io.sphere.sdk.products.queries;

import io.sphere.sdk.queries.*;

import java.util.Optional;

class ProductDataQueryModelBase<M> extends QueryModelImpl<M> {

    ProductDataQueryModelBase(Optional<? extends QueryModel<M>> parent, Optional<String> pathSegment) {
        super(parent, pathSegment);
    }

    public LocalizedStringQuerySortingModel<M> name() {
        return LocalizedStringQuerySortingModel.of(this, "name");
    }

    public LocalizedStringQueryModel<M> description() {
        return LocalizedStringQuerySortingModel.of(this, "description");
    }

    public LocalizedStringQuerySortingModel<M> slug() {
        return LocalizedStringQuerySortingModel.of(this, "slug");
    }

    public ProductVariantQueryModel<M> masterVariant() {
        return new ProductVariantQueryModel<>(Optional.of(this), "masterVariant");
    }

    public ProductVariantQueryModel<M> variants() {
        return new ProductVariantQueryModel<>(Optional.of(this), "variants");
    }
}

