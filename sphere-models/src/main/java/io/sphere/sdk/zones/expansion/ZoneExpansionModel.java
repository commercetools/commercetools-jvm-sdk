package io.sphere.sdk.zones.expansion;

import io.sphere.sdk.expansion.ExpansionModel;
import io.sphere.sdk.zones.Zone;

/**
  DSL class to create expansion path expressions.

 @param <T> the type for which the expansion path is
 */
public final class ZoneExpansionModel<T> extends ExpansionModel<T> {
    public ZoneExpansionModel(final String parentPath, final String path) {
        super(parentPath, path);
    }

    ZoneExpansionModel() {
        super();
    }

    public static ZoneExpansionModel<Zone> of() {
        return new ZoneExpansionModel<>();
    }
}
