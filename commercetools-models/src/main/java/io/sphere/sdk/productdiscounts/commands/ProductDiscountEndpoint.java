package io.sphere.sdk.productdiscounts.commands;

import io.sphere.sdk.client.JsonEndpoint;
import io.sphere.sdk.productdiscounts.ProductDiscount;

final class ProductDiscountEndpoint {
    static final JsonEndpoint<ProductDiscount> ENDPOINT = JsonEndpoint.of(ProductDiscount.typeReference(), "/product-discounts");
}
