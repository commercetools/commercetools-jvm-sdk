package io.sphere.sdk.annotations;

/**
 * This annotation allows to add a copy factory method for the given {@link #templateClass()}.
 */
public @interface CopyFactoryMethod {
    Class templateClass();
}
