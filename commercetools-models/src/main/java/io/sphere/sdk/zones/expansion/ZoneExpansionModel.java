package io.sphere.sdk.zones.expansion;

import io.sphere.sdk.expansion.ExpansionPathContainer;
import io.sphere.sdk.zones.Zone;

import java.util.List;

/**
 DSL class to create expansion path expressions.

 @param <T> the type for which the expansion path is
 */
public interface ZoneExpansionModel<T> extends ExpansionPathContainer<T> {

    static ZoneExpansionModel<Zone> of() {
        return new ZoneExpansionModelImpl<>();
    }

    static <T> ZoneExpansionModel<T> of(final List<String> parentPath, final String path) {
        return new ZoneExpansionModelImpl<>(parentPath, path);
    }
}
