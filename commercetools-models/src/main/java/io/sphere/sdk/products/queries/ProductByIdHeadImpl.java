package io.sphere.sdk.products.queries;

import io.sphere.sdk.products.Product;
import io.sphere.sdk.queries.MetaModelHeadDslBuilder;
import io.sphere.sdk.queries.MetaModelHeadDslImpl;


final class ProductByIdHeadImpl extends MetaModelHeadDslImpl<Product, Product, ProductByIdHead> implements ProductByIdHead {
    ProductByIdHeadImpl(final String id) {
        super(id, ProductEndpoint.ENDPOINT, ProductByIdHeadImpl::new);
    }

    public ProductByIdHeadImpl(MetaModelHeadDslBuilder<Product, Product, ProductByIdHead> builder) {
        super(builder);
    }
}
