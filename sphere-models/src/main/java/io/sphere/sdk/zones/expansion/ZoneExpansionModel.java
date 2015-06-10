package io.sphere.sdk.zones.expansion;

import io.sphere.sdk.queries.ExpansionModel;
import io.sphere.sdk.zones.Zone;

import java.util.Optional;

/**
  DSL class to create expansion path expressions.

 @param <T> the type for which the expansion path is
 */
public class ZoneExpansionModel<T> extends ExpansionModel<T> {
    public ZoneExpansionModel(final Optional<String> parentPath, final String path) {
        super(parentPath, Optional.of(path));
    }

    ZoneExpansionModel() {
        super();
    }

    public static ZoneExpansionModel<Zone> of() {
        return new ZoneExpansionModel<>();
    }
}
