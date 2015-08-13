package io.sphere.sdk.products.search;

import io.sphere.sdk.products.ProductProjection;
import io.sphere.sdk.search.*;

class ProductDataSearchModelBase extends SearchModelImpl<ProductProjection> {

    ProductDataSearchModelBase(final SearchModel<ProductProjection> parent, String pathSegment) {
        super(parent, pathSegment);
    }

    public ProductVariantSearchModel allVariants() {
        return new ProductVariantSearchModel(this, "variants");
    }

    public LocalizedStringSearchModel<ProductProjection, SimpleSearchSortDirection> name() {
        return new LocalizedStringSearchModel<>(this, "name");
    }

    public ReferenceSearchModel<ProductProjection, SimpleSearchSortDirection> categories() {
        return new ReferenceSearchModel<>(this, "categories");
    }

    public ReferenceSearchModel<ProductProjection, SimpleSearchSortDirection> productType() {
        return new ReferenceSearchModel<>(this, "productType");
    }

    public DateTimeSearchModel<ProductProjection, SimpleSearchSortDirection> createdAt() {
        return new DateTimeSearchModel<>(this, "createdAt");
    }

    public DateTimeSearchModel<ProductProjection, SimpleSearchSortDirection> lastModifiedAt() {
        return new DateTimeSearchModel<>(this, "lastModifiedAt");
    }
}
