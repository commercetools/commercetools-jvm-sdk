package io.sphere.sdk.shippingmethods.expansion;

import io.sphere.sdk.expansion.ExpansionModel;
import io.sphere.sdk.expansion.ExpansionPathContainer;

import java.util.List;

final class ZoneRateExpansionModelImpl<T> extends ExpansionModel<T> implements ZoneRateExpansionModel<T> {
    ZoneRateExpansionModelImpl(final List<String> parentPath, final String path) {
        super(parentPath, path);
    }

    @Override
    public ExpansionPathContainer<T> zone() {
        return expansionPath("zone");
    }
}
