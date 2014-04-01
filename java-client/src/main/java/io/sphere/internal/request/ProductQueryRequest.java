package io.sphere.internal.request;

import io.sphere.client.QueryRequest;
import io.sphere.client.model.products.BackendProductProjection;
import io.sphere.client.shop.CategoryTree;
import io.sphere.client.shop.model.Product;
import io.sphere.internal.ProductConversion;

public class ProductQueryRequest extends QueryRequestMapping<BackendProductProjection, Product> {
    private final CategoryTree categoryTree;

    protected ProductQueryRequest(QueryRequest<BackendProductProjection> delegate, CategoryTree categoryTree) {
        super(delegate);
        this.categoryTree = categoryTree;
    }

    @Override
    protected Product convert(BackendProductProjection backendProduct) {
        return ProductConversion.fromBackendProductProjection(backendProduct, categoryTree);
    }
}
