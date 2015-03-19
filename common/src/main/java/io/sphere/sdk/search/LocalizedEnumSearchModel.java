package io.sphere.sdk.search;

import java.util.Optional;

public class LocalizedEnumSearchModel<T, S extends SearchSortDirection> extends SearchModelImpl<T> {

    public LocalizedEnumSearchModel(final Optional<? extends SearchModel<T>> parent, final String pathSegment) {
        super(parent, pathSegment);
    }

    public StringSearchModel<T, S> key() {
        return new StringSearchModel<>(Optional.of(this), "key");
    }

    public LocalizedStringsSearchModel<T, S> label() {
        return new LocalizedStringsSearchModel<>(Optional.of(this), "label");
    }
}
