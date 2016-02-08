package io.sphere.sdk.products.queries;

import io.sphere.sdk.products.Product;
import io.sphere.sdk.products.expansion.ProductExpansionModel;
import io.sphere.sdk.queries.MetaModelQueryDslBuilder;
import io.sphere.sdk.queries.MetaModelQueryDslImpl;

/**
 {@doc.gen summary products}
 */
final class ProductQueryImpl extends MetaModelQueryDslImpl<Product, ProductQuery, ProductQueryModel, ProductExpansionModel<Product>> implements ProductQuery {
    ProductQueryImpl(){
        super(ProductEndpoint.ENDPOINT.endpoint(), ProductQuery.resultTypeReference(), ProductQueryModel.of(), ProductExpansionModel.of(), ProductQueryImpl::new);
    }

    private ProductQueryImpl(final MetaModelQueryDslBuilder<Product, ProductQuery, ProductQueryModel, ProductExpansionModel<Product>> builder) {
        super(builder);
    }
}