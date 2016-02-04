package io.sphere.sdk.shippingmethods.expansion;

import io.sphere.sdk.expansion.ExpansionModelImpl;
import io.sphere.sdk.zones.expansion.ZoneExpansionModel;

import java.util.List;

final class ZoneRateExpansionModelImpl<T> extends ExpansionModelImpl<T> implements ZoneRateExpansionModel<T> {
    ZoneRateExpansionModelImpl(final List<String> parentPath, final String path) {
        super(parentPath, path);
    }

    @Override
    public ZoneExpansionModel<T> zone() {
        return ZoneExpansionModel.of(pathExpression(), "zone");
    }
}
