package io.sphere.sdk.products.search;

import io.sphere.sdk.products.ProductProjection;
import io.sphere.sdk.search.*;

import javax.annotation.Nullable;

class ProductDataSearchModelBase extends SearchModelImpl<ProductProjection> {

    public ProductDataSearchModelBase(@Nullable final SearchModel<ProductProjection> parent, @Nullable final String pathSegment) {
        super(parent, pathSegment);
    }

    public ProductVariantSearchModel allVariants() {
        return new ProductVariantSearchModel(this, "variants");
    }

    public LocalizedStringSearchModel<ProductProjection, DirectionlessSearchSortModel<ProductProjection>> name() {
        return new LocalizedStringSearchModel<>(this, "name", new DirectionlessSearchSortBuilder<>());
    }

    public ReferenceSearchModel<ProductProjection, DirectionlessSearchSortModel<ProductProjection>> categories() {
        return new ReferenceSearchModel<>(this, "categories", new DirectionlessSearchSortBuilder<>());
    }

    public ReferenceSearchModel<ProductProjection, DirectionlessSearchSortModel<ProductProjection>> productType() {
        return new ReferenceSearchModel<>(this, "productType", new DirectionlessSearchSortBuilder<>());
    }

    public DateTimeSearchModel<ProductProjection, DirectionlessSearchSortModel<ProductProjection>> createdAt() {
        return new DateTimeSearchModel<>(this, "createdAt", new DirectionlessSearchSortBuilder<>());
    }

    public DateTimeSearchModel<ProductProjection, DirectionlessSearchSortModel<ProductProjection>> lastModifiedAt() {
        return new DateTimeSearchModel<>(this, "lastModifiedAt", new DirectionlessSearchSortBuilder<>());
    }
}
