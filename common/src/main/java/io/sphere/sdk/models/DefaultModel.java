package io.sphere.sdk.models;

public interface DefaultModel<T> extends Timestamped, Versioned<T> {
    Reference<T> toReference();
}
