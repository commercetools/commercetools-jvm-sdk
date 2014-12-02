package io.sphere.sdk.search;

import java.util.Optional;

public class LocalizedEnumSearchModel<T> extends SearchModelImpl<T> {

    public LocalizedEnumSearchModel(final Optional<? extends SearchModel<T>> parent, final String pathSegment) {
        super(parent, pathSegment);
    }

    public StringSearchModel<T> key() {
        return new StringSearchModel<>(Optional.of(this), "key");
    }

    public LocalizedStringsSearchModel<T> label() {
        return new LocalizedStringsSearchModel<>(Optional.of(this), "label");
    }
}
