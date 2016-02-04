package io.sphere.sdk.shippingmethods.expansion;

import io.sphere.sdk.expansion.ExpansionPathContainer;

public interface ZoneRateExpansionModel<T> {
    ExpansionPathContainer<T> zone();
}
