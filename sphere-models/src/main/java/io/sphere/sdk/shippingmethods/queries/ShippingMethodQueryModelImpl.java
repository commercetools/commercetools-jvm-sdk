package io.sphere.sdk.shippingmethods.queries;

import io.sphere.sdk.queries.*;
import io.sphere.sdk.shippingmethods.ShippingMethod;
import io.sphere.sdk.taxcategories.TaxCategory;

final class ShippingMethodQueryModelImpl extends ResourceQueryModelImpl<ShippingMethod> implements ShippingMethodQueryModel {

    ShippingMethodQueryModelImpl(final QueryModel<ShippingMethod> parent, final String pathSegment) {
        super(parent, pathSegment);
    }

    @Override
    public StringQuerySortingModel<ShippingMethod> name() {
        return stringModel("name");
    }

    @Override
    public ReferenceQueryModel<ShippingMethod, TaxCategory> taxCategory() {
        return referenceModel("taxCategory");
    }

    @Override
    public ZoneRateCollectionQueryModel<ShippingMethod> zoneRates() {
        return new ZoneRateCollectionQueryModelImpl<>(this, "zoneRates");
    }

    @Override
    public BooleanQueryModel<ShippingMethod> isDefault() {
        return booleanModel("isDefault");
    }
}
