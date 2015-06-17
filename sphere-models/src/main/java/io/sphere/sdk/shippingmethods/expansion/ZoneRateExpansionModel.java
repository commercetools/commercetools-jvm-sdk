package io.sphere.sdk.shippingmethods.expansion;

import io.sphere.sdk.queries.ExpansionModel;
import io.sphere.sdk.queries.ExpansionPath;

import java.util.Optional;

public class ZoneRateExpansionModel<T> extends ExpansionModel<T> {
    ZoneRateExpansionModel(final Optional<String> parentPath, final String path) {
        super(parentPath, Optional.of(path));
    }

    ZoneRateExpansionModel() {
        super();
    }

    public ExpansionPath<T> zone() {
        return expansionPath("zone");
    }
}
