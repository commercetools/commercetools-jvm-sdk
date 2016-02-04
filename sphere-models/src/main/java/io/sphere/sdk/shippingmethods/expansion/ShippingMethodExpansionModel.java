package io.sphere.sdk.shippingmethods.expansion;

import io.sphere.sdk.expansion.ExpansionPathContainer;
import io.sphere.sdk.shippingmethods.ShippingMethod;
import io.sphere.sdk.taxcategories.expansion.TaxCategoryExpansionModel;
import io.sphere.sdk.zones.expansion.ZoneExpansionModel;

import java.util.List;

public interface ShippingMethodExpansionModel<T> extends ExpansionPathContainer<T> {
    TaxCategoryExpansionModel<T> taxCategory();

    ZoneRateExpansionModel<T> zoneRates();

    ZoneRateExpansionModel<T> zoneRates(int index);

    ZoneExpansionModel<T> zones();

    ZoneExpansionModel<T> zones(int index);


    static ShippingMethodExpansionModel<ShippingMethod> of() {
        return new ShippingMethodExpansionModelImpl<>();
    }

    static <T> ShippingMethodExpansionModel<T> of(final List<String> parentPath, final String path) {
        return new ShippingMethodExpansionModelImpl<>(parentPath, path);
    }
}
