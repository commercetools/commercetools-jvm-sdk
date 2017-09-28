package io.sphere.sdk.carts.queries;

import io.sphere.sdk.queries.OptionalQueryModel;
import io.sphere.sdk.queries.StringQueryModel;

public interface ShippingRateInputQueryModel<T> extends OptionalQueryModel<T> {
    StringQueryModel<T> key();

}