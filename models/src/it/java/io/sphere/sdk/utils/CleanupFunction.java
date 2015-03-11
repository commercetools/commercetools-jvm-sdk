package io.sphere.sdk.utils;

@FunctionalInterface
public interface CleanupFunction<T> {
    void cleanup(T target);
}
