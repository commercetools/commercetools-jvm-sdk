package io.sphere.internal.request;

import io.sphere.client.QueryRequest;
import io.sphere.client.model.products.BackendProduct;
import io.sphere.client.shop.CategoryTree;
import io.sphere.client.shop.model.Product;
import io.sphere.internal.ProductConversion;

public class ProductQueryRequest extends QueryRequestMapping<BackendProduct, Product> {
    private final CategoryTree categoryTree;

    protected ProductQueryRequest(QueryRequest<BackendProduct> delegate, CategoryTree categoryTree) {
        super(delegate);
        this.categoryTree = categoryTree;
    }

    @Override
    protected Product convert(BackendProduct backendProduct) {
        return ProductConversion.fromBackendProduct(backendProduct, categoryTree);
    }
}
