package io.sphere.sdk.products.search;

import io.sphere.sdk.categories.Category;
import io.sphere.sdk.products.ProductProjection;
import io.sphere.sdk.search.*;

import java.util.Optional;

class ProductDataSearchModelBase extends SearchModelImpl<ProductProjection> {

    ProductDataSearchModelBase(Optional<? extends SearchModel<ProductProjection>> parent, Optional<String> pathSegment) {
        super(parent, pathSegment);
    }

    public ProductVariantSearchModel allVariants() {
        return new ProductVariantSearchModel(Optional.of(this), "variants");
    }

    public ReferenceSearchModel<ProductProjection, Category> categories() {
        return new ReferenceSearchModel<>(Optional.of(this), "categories");
    }

    public LocalizedStringsSearchModel<ProductProjection, SimpleSearchSortDirection> name() {
        return new LocalizedStringsSearchModel<>(Optional.of(this), "name");
    }

    public DateTimeSearchModel<ProductProjection, SimpleSearchSortDirection> createdAt() {
        return new DateTimeSearchModel<>(Optional.of(this), "createdAt");
    }

    public DateTimeSearchModel<ProductProjection, SimpleSearchSortDirection> lastModifiedAt() {
        return new DateTimeSearchModel<>(Optional.of(this), "lastModifiedAt");
    }
}
