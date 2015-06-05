package io.sphere.sdk.shippingmethods.queries;

import io.sphere.sdk.queries.*;
import io.sphere.sdk.shippingmethods.ShippingMethod;
import io.sphere.sdk.taxcategories.TaxCategory;

import java.util.Optional;

public class ShippingMethodQueryModel<T> extends DefaultModelQueryModelImpl<T> {
    public static ShippingMethodQueryModel<ShippingMethod> of() {
        return new ShippingMethodQueryModel<>(Optional.<QueryModel<ShippingMethod>>empty(), Optional.<String>empty());
    }

    private ShippingMethodQueryModel(final Optional<? extends QueryModel<T>> parent, final Optional<String> pathSegment) {
        super(parent, pathSegment);
    }

    public StringQuerySortingModel<T> name() {
        return new StringQuerySortingModel<>(Optional.of(this), "name");
    }

    public ReferenceQueryModel<T, TaxCategory> taxCategory() {
        return new ReferenceQueryModel<>(Optional.of(this), "taxCategory");
    }

    public ZoneRateListQueryModel <T> zoneRates() {
        return new ZoneRateListQueryModel<>(Optional.of(this), "zoneRates");
    }

    public BooleanQueryModel<T> isDefault() {
        return new BooleanQueryModel<>(Optional.of(this), "isDefault");
    }
}
