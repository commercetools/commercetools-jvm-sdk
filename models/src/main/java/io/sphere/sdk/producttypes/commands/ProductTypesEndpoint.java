package io.sphere.sdk.producttypes.commands;

import io.sphere.sdk.client.JsonEndpoint;
import io.sphere.sdk.producttypes.ProductType;

final class ProductTypesEndpoint {
    static final JsonEndpoint<ProductType> ENDPOINT = JsonEndpoint.of(ProductType.typeReference(), "/product-types");
}
