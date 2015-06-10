package io.sphere.sdk.shippingmethods.queries;

import io.sphere.sdk.queries.*;
import io.sphere.sdk.shippingmethods.ShippingMethod;
import io.sphere.sdk.taxcategories.TaxCategory;

import java.util.Optional;

public class ShippingMethodQueryModel extends DefaultModelQueryModelImpl<ShippingMethod> {
    public static ShippingMethodQueryModel of() {
        return new ShippingMethodQueryModel(Optional.<QueryModel<ShippingMethod>>empty(), Optional.<String>empty());
    }

    private ShippingMethodQueryModel(final Optional<? extends QueryModel<ShippingMethod>> parent, final Optional<String> pathSegment) {
        super(parent, pathSegment);
    }

    public StringQuerySortingModel<ShippingMethod> name() {
        return new StringQuerySortingModel<>(Optional.of(this), "name");
    }

    public ReferenceQueryModel<ShippingMethod, TaxCategory> taxCategory() {
        return new ReferenceQueryModel<>(Optional.of(this), "taxCategory");
    }

    public ZoneRateCollectionQueryModel<ShippingMethod> zoneRates() {
        return new ZoneRateCollectionQueryModel<>(Optional.of(this), "zoneRates");
    }

    public BooleanQueryModel<ShippingMethod> isDefault() {
        return new BooleanQueryModel<>(Optional.of(this), "isDefault");
    }
}
