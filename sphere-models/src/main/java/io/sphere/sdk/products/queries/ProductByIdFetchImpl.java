package io.sphere.sdk.products.queries;

import io.sphere.sdk.products.Product;
import io.sphere.sdk.products.expansion.ProductExpansionModel;
import io.sphere.sdk.queries.MetaModelFetchDslBuilder;
import io.sphere.sdk.queries.MetaModelFetchDslImpl;

final class ProductByIdFetchImpl extends MetaModelFetchDslImpl<Product, ProductByIdFetch, ProductExpansionModel<Product>> implements ProductByIdFetch {
    ProductByIdFetchImpl(final String id) {
        super(id, ProductEndpoint.ENDPOINT, ProductExpansionModel.of(), ProductByIdFetchImpl::new);
    }

    public ProductByIdFetchImpl(MetaModelFetchDslBuilder<Product, ProductByIdFetch, ProductExpansionModel<Product>> builder) {
        super(builder);
    }
}
