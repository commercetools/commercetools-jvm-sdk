package io.sphere.sdk.carts.queries;

import io.sphere.sdk.queries.OptionalQueryModel;
import io.sphere.sdk.queries.QueryModel;
import io.sphere.sdk.queries.StringQuerySortingModel;

public interface ShippingRateInputQueryModel<T> extends OptionalQueryModel<T> ,QueryModel<T> {
    StringQuerySortingModel<T> key();

}