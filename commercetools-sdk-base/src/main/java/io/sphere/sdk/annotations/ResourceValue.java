package io.sphere.sdk.annotations;

/**
 * This annotation marks an interface as a resource representation.
 */
public @interface ResourceValue {
    String additionalConstructorEndContent() default "";
}
