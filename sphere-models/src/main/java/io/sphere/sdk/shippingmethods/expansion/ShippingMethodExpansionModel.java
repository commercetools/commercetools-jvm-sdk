package io.sphere.sdk.shippingmethods.expansion;

import io.sphere.sdk.expansion.ExpandedModel;
import io.sphere.sdk.expansion.ExpansionPathContainer;
import io.sphere.sdk.shippingmethods.ShippingMethod;

import javax.annotation.Nullable;
import java.util.List;

/**
  DSL class to create expansion path expressions.

 @param <T> the type for which the expansion path is
 */
public final class ShippingMethodExpansionModel<T> extends ExpandedModel<T> {
    public ShippingMethodExpansionModel(final List<String> parentPath, @Nullable final String path) {
        super(parentPath, path);
    }

    ShippingMethodExpansionModel() {
        super();
    }

    public ExpansionPathContainer<T> taxCategory() {
        return expansionPath("taxCategory");
    }

    public ZoneRateExpansionModel<T> zoneRates() {
        return zoneRates("*");
    }

    public ZoneRateExpansionModel<T> zoneRates(final int index) {
        return zoneRates("" + index);
    }

    public ExpansionPathContainer<T> zones() {
        return zoneRates().zone();
    }

    public ExpansionPathContainer<T> zones(final int index) {
        return zoneRates(index).zone();
    }

    private ZoneRateExpansionModel<T> zoneRates(final String s) {
        return new ZoneRateExpansionModelImpl<>(pathExpression(), "zoneRates[" + s + "]");
    }

    public static ShippingMethodExpansionModel<ShippingMethod> of() {
        return new ShippingMethodExpansionModel<>();
    }
}
