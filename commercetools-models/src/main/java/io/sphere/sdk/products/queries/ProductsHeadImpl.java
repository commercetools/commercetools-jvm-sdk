package io.sphere.sdk.products.queries;


import io.sphere.sdk.products.Product;
import io.sphere.sdk.queries.MetaModelHeadDslBuilder;
import io.sphere.sdk.queries.MetaModelHeadDslImpl;


final class ProductsHeadImpl extends MetaModelHeadDslImpl<Product, Product, ProductsHead> implements ProductsHead {
    ProductsHeadImpl() {
        super(null, ProductEndpoint.ENDPOINT, ProductsHeadImpl::new);
    }

    public ProductsHeadImpl(MetaModelHeadDslBuilder<Product, Product, ProductsHead> builder) {
        super(builder);
    }
}
