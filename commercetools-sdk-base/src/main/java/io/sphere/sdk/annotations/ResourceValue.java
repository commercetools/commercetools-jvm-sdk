package io.sphere.sdk.annotations;

/**
 * This annotation marks an interface as a resource representation.
 */
public @interface ResourceValue {
    /**
     * If set to true, the generated resource value class will be abstract so that it can be further customized.
     *
     * @return true iff. the generated class should be abstract
     */
    boolean abstractResourceClass() default false;
}
