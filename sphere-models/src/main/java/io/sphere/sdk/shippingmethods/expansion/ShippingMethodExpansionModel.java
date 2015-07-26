package io.sphere.sdk.shippingmethods.expansion;

import io.sphere.sdk.expansion.ExpansionModel;
import io.sphere.sdk.expansion.ExpansionPath;
import io.sphere.sdk.shippingmethods.ShippingMethod;

import javax.annotation.Nullable;

/**
  DSL class to create expansion path expressions.

 @param <T> the type for which the expansion path is
 */
public class ShippingMethodExpansionModel<T> extends ExpansionModel<T> {
    public ShippingMethodExpansionModel(@Nullable final String parentPath, @Nullable final String path) {
        super(parentPath, path);
    }

    ShippingMethodExpansionModel() {
        super();
    }

    public ExpansionPath<T> taxCategory() {
        return expansionPath("taxCategory");
    }

    public ZoneRateExpansionModel<T> zoneRates() {
        return zoneRates("*");
    }

    public ZoneRateExpansionModel<T> zoneRates(final int index) {
        return zoneRates("" + index);
    }

    public ExpansionPath<T> zones() {
        return zoneRates().zone();
    }

    public ExpansionPath<T> zones(final int index) {
        return zoneRates(index).zone();
    }

    private ZoneRateExpansionModel<T> zoneRates(final String s) {
        return new ZoneRateExpansionModel<>(pathExpression(), "zoneRates[" + s + "]");
    }

    public static ShippingMethodExpansionModel<ShippingMethod> of() {
        return new ShippingMethodExpansionModel<>();
    }
}
