package io.sphere.sdk.search;

import io.sphere.sdk.models.Base;

public abstract class Term<T> extends Base {
    protected final T value;

    protected Term(final T value) {
        this.value = value;
    }

    public T value() {
        return value;
    }

    public abstract String render();
}
