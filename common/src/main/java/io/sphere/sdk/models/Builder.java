package io.sphere.sdk.models;

import java.util.function.Supplier;

public interface Builder<T> extends Supplier<T> {
    T build();

    default T get() {
        return build();
    }
}
