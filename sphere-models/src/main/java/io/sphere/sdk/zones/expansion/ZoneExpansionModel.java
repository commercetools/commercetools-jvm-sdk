package io.sphere.sdk.zones.expansion;

import io.sphere.sdk.expansion.ExpansionModel;
import io.sphere.sdk.expansion.ExpansionPath;
import io.sphere.sdk.expansion.ExpansionPathContainer;
import io.sphere.sdk.zones.Zone;

import java.util.List;

/**
  DSL class to create expansion path expressions.

 @param <T> the type for which the expansion path is
 */
public final class ZoneExpansionModel<T> extends ExpansionModel<T> implements ExpansionPathContainer<T> {
    ZoneExpansionModel(final List<String> parentPath, final String path) {
        super(parentPath, path);
    }

    ZoneExpansionModel() {
        super();
    }

    public static ZoneExpansionModel<Zone> of() {
        return new ZoneExpansionModel<>();
    }

    public static <T> ZoneExpansionModel<T> of(final List<String> parentPath, final String path) {
        return new ZoneExpansionModel<>(parentPath, path);
    }

    @Override
    public List<ExpansionPath<T>> expansionPaths() {
        return buildExpansionPaths();
    }
}
