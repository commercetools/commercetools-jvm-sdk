package io.sphere.sdk.shippingmethods.expansion;

import io.sphere.sdk.expansion.ExpansionModel;
import io.sphere.sdk.expansion.ReferenceExpansionSupport;

import java.util.List;

public class ZoneRateExpansionModel<T> extends ExpansionModel<T> {
    ZoneRateExpansionModel(final List<String> parentPath, final String path) {
        super(parentPath, path);
    }

    ZoneRateExpansionModel() {
        super();
    }

    public ReferenceExpansionSupport<T> zone() {
        return expansionPath("zone");
    }
}
