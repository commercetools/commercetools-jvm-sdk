package io.sphere.sdk.shippingmethods.queries;

import io.sphere.sdk.queries.DefaultModelQueryModelImpl;
import io.sphere.sdk.queries.QueryModel;
import io.sphere.sdk.queries.StringQuerySortingModel;
import io.sphere.sdk.shippingmethods.ShippingMethod;

import java.util.Optional;

public class ShippingMethodQueryModel extends DefaultModelQueryModelImpl<ShippingMethod> {
    static ShippingMethodQueryModel get() {
        return new ShippingMethodQueryModel(Optional.<QueryModel<ShippingMethod>>empty(), Optional.<String>empty());
    }

    private ShippingMethodQueryModel(final Optional<? extends QueryModel<ShippingMethod>> parent, final Optional<String> pathSegment) {
        super(parent, pathSegment);
    }

    public StringQuerySortingModel<ShippingMethod> name() {
        return new StringQuerySortingModel<>(Optional.of(this), "name");
    }
}
