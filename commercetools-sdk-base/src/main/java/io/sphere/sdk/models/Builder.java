package io.sphere.sdk.models;

import java.util.function.Supplier;

/**
 * A {@link io.sphere.sdk.models.Builder} is a mutable data container to
 * create an immutable instance of another type.
 * @param <T> the type of the class where the element should be build
 */
public interface Builder<T> extends Supplier<T> {
    /**
     * Creates a new instance of T with the values of this builder.
     *
     * @return new instance of T.
     */
    T build();

    /**
     * Alias for {@link Builder#build()}.
     * @return a new instance of T.
     */
    default T get() {
        return build();
    }
}
