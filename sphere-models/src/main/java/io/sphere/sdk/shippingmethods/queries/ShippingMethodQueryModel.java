package io.sphere.sdk.shippingmethods.queries;

import io.sphere.sdk.queries.*;
import io.sphere.sdk.shippingmethods.ShippingMethod;
import io.sphere.sdk.taxcategories.TaxCategory;

public class ShippingMethodQueryModel extends ResourceQueryModelImpl<ShippingMethod> {
    public static ShippingMethodQueryModel of() {
        return new ShippingMethodQueryModel(null, null);
    }

    private ShippingMethodQueryModel(final QueryModel<ShippingMethod> parent, final String pathSegment) {
        super(parent, pathSegment);
    }

    public StringQuerySortingModel<ShippingMethod> name() {
        return stringModel("name");
    }

    public ReferenceQueryModel<ShippingMethod, TaxCategory> taxCategory() {
        return referenceModel("taxCategory");
    }

    public ZoneRateCollectionQueryModel<ShippingMethod> zoneRates() {
        return new ZoneRateCollectionQueryModelImpl<>(this, "zoneRates");
    }

    public BooleanQueryModel<ShippingMethod> isDefault() {
        return booleanModel("isDefault");
    }
}
