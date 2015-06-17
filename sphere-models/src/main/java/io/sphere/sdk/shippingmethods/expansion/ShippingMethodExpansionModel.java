package io.sphere.sdk.shippingmethods.expansion;

import io.sphere.sdk.expansion.ExpansionModel;
import io.sphere.sdk.expansion.ExpansionPath;
import io.sphere.sdk.shippingmethods.ShippingMethod;

import java.util.Optional;

/**
  DSL class to create expansion path expressions.

 @param <T> the type for which the expansion path is
 */
public class ShippingMethodExpansionModel<T> extends ExpansionModel<T> {
    public ShippingMethodExpansionModel(final Optional<String> parentPath, final String path) {
        super(parentPath, Optional.of(path));
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
        return new ZoneRateExpansionModel<>(pathExpressionOption(), "zoneRates[" + s + "]");
    }

    public static ShippingMethodExpansionModel<ShippingMethod> of() {
        return new ShippingMethodExpansionModel<>();
    }
}
