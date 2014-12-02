package io.sphere.sdk.search;

import java.util.Optional;

public class EnumSearchModel<T> extends SearchModelImpl<T> {

    public EnumSearchModel(final Optional<? extends SearchModel<T>> parent, final String pathSegment) {
        super(parent, pathSegment);
    }

    public StringSearchModel<T> key() {
        return new StringSearchModel<>(Optional.of(this), "key");
    }

    public StringSearchModel<T> label() {
        return new StringSearchModel<>(Optional.of(this), "label");
    }
}
