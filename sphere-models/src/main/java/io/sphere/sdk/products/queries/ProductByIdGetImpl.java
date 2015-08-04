package io.sphere.sdk.products.queries;

import io.sphere.sdk.products.Product;
import io.sphere.sdk.products.expansion.ProductExpansionModel;
import io.sphere.sdk.queries.MetaModelFetchDslBuilder;
import io.sphere.sdk.queries.MetaModelGetDslImpl;

final class ProductByIdGetImpl extends MetaModelGetDslImpl<Product, Product, ProductByIdGet, ProductExpansionModel<Product>> implements ProductByIdGet {
    ProductByIdGetImpl(final String id) {
        super(id, ProductEndpoint.ENDPOINT, ProductExpansionModel.of(), ProductByIdGetImpl::new);
    }

    public ProductByIdGetImpl(MetaModelFetchDslBuilder<Product, Product, ProductByIdGet, ProductExpansionModel<Product>> builder) {
        super(builder);
    }
}
