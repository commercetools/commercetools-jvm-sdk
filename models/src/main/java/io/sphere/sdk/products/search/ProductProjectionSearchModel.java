package io.sphere.sdk.products.search;

import io.sphere.sdk.categories.Category;
import io.sphere.sdk.products.ProductProjection;
import io.sphere.sdk.search.*;

import java.util.Optional;

public class ProductProjectionSearchModel extends ProductDataSearchModelBase {

    private ProductProjectionSearchModel(final Optional<? extends SearchModel<ProductProjection>> parent, final Optional<String> pathSegment) {
        super(parent, pathSegment);
    }

    static ProductProjectionSearchModel get() {
        return new ProductProjectionSearchModel(Optional.empty(), Optional.empty());
    }

    @Override
    public ProductVariantSearchModel variants() {
        return super.variants();
    }

    @Override
    public ReferenceSearchModel<ProductProjection, Category> categories() {
        return super.categories();
    }

    @Override
    public LocalizedStringsSearchModel<ProductProjection, SimpleSearchSortDirection> name() {
        return super.name();
    }

    @Override
    public DateTimeSearchModel<ProductProjection, SimpleSearchSortDirection> createdAt() {
        return super.createdAt();
    }

    @Override
    public DateTimeSearchModel<ProductProjection, SimpleSearchSortDirection> lastModifiedAt() {
        return super.lastModifiedAt();
    }
}
