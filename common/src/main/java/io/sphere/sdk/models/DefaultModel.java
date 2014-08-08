package io.sphere.sdk.models;

public interface DefaultModel<T> extends Timestamped, Versioned<T>, Referenceable<T> {
    Reference<T> toReference();
}
