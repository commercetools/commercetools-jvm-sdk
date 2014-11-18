package io.sphere.sdk.products.queries;

import io.sphere.sdk.http.JsonEndpoint;
import io.sphere.sdk.products.ProductProjection;

final class ProductProjectionsEndpoint {
    static JsonEndpoint<ProductProjection> ENDPOINT = JsonEndpoint.of(ProductProjection.typeReference(), "/product-projections");
}
