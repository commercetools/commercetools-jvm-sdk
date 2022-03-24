package io.sphere.sdk.productselections.queries;

import io.sphere.sdk.client.JsonEndpoint;
import io.sphere.sdk.productselections.ProductSelection;

final class ProductSelectionEndpoint {
    static final JsonEndpoint<ProductSelection> ENDPOINT = JsonEndpoint.of(ProductSelection.typeReference(), "/product-selections");
}
