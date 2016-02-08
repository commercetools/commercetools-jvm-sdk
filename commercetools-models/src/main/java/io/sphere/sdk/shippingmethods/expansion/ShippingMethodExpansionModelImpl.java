package io.sphere.sdk.shippingmethods.expansion;

import io.sphere.sdk.expansion.ExpansionModelImpl;
import io.sphere.sdk.taxcategories.expansion.TaxCategoryExpansionModel;
import io.sphere.sdk.zones.expansion.ZoneExpansionModel;

import javax.annotation.Nullable;
import java.util.List;

/**
  DSL class to create expansion path expressions.

 @param <T> the type for which the expansion path is
 */
final class ShippingMethodExpansionModelImpl<T> extends ExpansionModelImpl<T> implements ShippingMethodExpansionModel<T> {
    ShippingMethodExpansionModelImpl(final List<String> parentPath, @Nullable final String path) {
        super(parentPath, path);
    }

    ShippingMethodExpansionModelImpl() {
        super();
    }

    @Override
    public TaxCategoryExpansionModel<T> taxCategory() {
        return TaxCategoryExpansionModel.of(buildPathExpression(), "taxCategory");
    }

    @Override
    public ZoneRateExpansionModel<T> zoneRates() {
        return zoneRates("*");
    }

    @Override
    public ZoneRateExpansionModel<T> zoneRates(final int index) {
        return zoneRates("" + index);
    }

    @Override
    public ZoneExpansionModel<T> zones() {
        return zoneRates().zone();
    }

    @Override
    public ZoneExpansionModel<T> zones(final int index) {
        return zoneRates(index).zone();
    }

    private ZoneRateExpansionModel<T> zoneRates(final String s) {
        return new ZoneRateExpansionModelImpl<>(pathExpression(), "zoneRates[" + s + "]");
    }
}
