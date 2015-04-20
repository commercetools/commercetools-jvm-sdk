package io.sphere.sdk.utils.functional;

@FunctionalInterface
public interface TriFunction<R, A, B, C> {
    public R apply(A a, B b, C c);
}
