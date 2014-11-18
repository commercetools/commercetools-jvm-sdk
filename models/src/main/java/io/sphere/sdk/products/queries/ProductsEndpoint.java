package io.sphere.sdk.products.queries;

import io.sphere.sdk.http.JsonEndpoint;
import io.sphere.sdk.products.Product;

final class ProductsEndpoint {
    static JsonEndpoint<Product> ENDPOINT = JsonEndpoint.of(Product.typeReference(), "/products");
}
