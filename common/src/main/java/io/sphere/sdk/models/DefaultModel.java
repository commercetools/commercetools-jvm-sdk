package io.sphere.sdk.models;

public interface DefaultModel<T> extends Timestamped, Versioned {
    Reference<T> toReference();
}
