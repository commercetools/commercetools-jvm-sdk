package io.sphere.sdk.products.queries;

import io.sphere.sdk.products.Product;
import io.sphere.sdk.queries.MetaModelHeadDslBuilder;
import io.sphere.sdk.queries.MetaModelHeadDslImpl;


import static io.sphere.sdk.client.SphereRequestUtils.urlEncode;

final class ProductByKeyHeadImpl extends MetaModelHeadDslImpl<Product, Product, ProductByKeyHead> implements ProductByKeyHead {
    ProductByKeyHeadImpl(final String key) {
        super("key=" + urlEncode(key), ProductEndpoint.ENDPOINT, ProductByKeyHeadImpl::new);
    }

    public ProductByKeyHeadImpl(final MetaModelHeadDslBuilder<Product, Product, ProductByKeyHead> builder) {
        super(builder);
    }
}
