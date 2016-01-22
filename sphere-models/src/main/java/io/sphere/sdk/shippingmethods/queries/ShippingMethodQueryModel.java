package io.sphere.sdk.shippingmethods.queries;

import io.sphere.sdk.queries.BooleanQueryModel;
import io.sphere.sdk.queries.ReferenceQueryModel;
import io.sphere.sdk.queries.ResourceQueryModel;
import io.sphere.sdk.queries.StringQuerySortingModel;
import io.sphere.sdk.shippingmethods.ShippingMethod;
import io.sphere.sdk.taxcategories.TaxCategory;

public interface ShippingMethodQueryModel extends ResourceQueryModel<ShippingMethod> {
    StringQuerySortingModel<ShippingMethod> name();

    ReferenceQueryModel<ShippingMethod, TaxCategory> taxCategory();

    ZoneRateCollectionQueryModel<ShippingMethod> zoneRates();

    BooleanQueryModel<ShippingMethod> isDefault();

    static ShippingMethodQueryModel of() {
        return new ShippingMethodQueryModelImpl(null, null);
    }
}
