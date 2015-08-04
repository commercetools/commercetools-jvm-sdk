package io.sphere.sdk.products.queries;

import io.sphere.sdk.products.Product;
import io.sphere.sdk.products.expansion.ProductExpansionModel;
import io.sphere.sdk.queries.MetaModelGetDslBuilder;
import io.sphere.sdk.queries.MetaModelGetDslImpl;

final class ProductByIdGetImpl extends MetaModelGetDslImpl<Product, Product, ProductByIdGet, ProductExpansionModel<Product>> implements ProductByIdGet {
    ProductByIdGetImpl(final String id) {
        super(id, ProductEndpoint.ENDPOINT, ProductExpansionModel.of(), ProductByIdGetImpl::new);
    }

    public ProductByIdGetImpl(MetaModelGetDslBuilder<Product, Product, ProductByIdGet, ProductExpansionModel<Product>> builder) {
        super(builder);
    }
}
