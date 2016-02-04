package io.sphere.sdk.zones.expansion;

import io.sphere.sdk.expansion.ExpansionModelImpl;
import io.sphere.sdk.expansion.ExpansionPath;

import java.util.List;

final class ZoneExpansionModelImpl<T> extends ExpansionModelImpl<T> implements ZoneExpansionModel<T> {
    ZoneExpansionModelImpl(final List<String> parentPath, final String path) {
        super(parentPath, path);
    }

    ZoneExpansionModelImpl() {
        super();
    }

    @Override
    public List<ExpansionPath<T>> expansionPaths() {
        return buildExpansionPaths();
    }
}
