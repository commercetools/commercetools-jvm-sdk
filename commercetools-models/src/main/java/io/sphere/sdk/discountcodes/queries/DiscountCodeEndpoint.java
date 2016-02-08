package io.sphere.sdk.discountcodes.queries;

import io.sphere.sdk.client.JsonEndpoint;
import io.sphere.sdk.discountcodes.DiscountCode;

final class DiscountCodeEndpoint {
    public static final JsonEndpoint<DiscountCode> ENDPOINT = JsonEndpoint.of(DiscountCode.typeReference(), "/discount-codes");
}
