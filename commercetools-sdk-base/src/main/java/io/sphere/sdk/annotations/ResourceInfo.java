package io.sphere.sdk.annotations;

/**
 * This annotation is used to generate commands and queries for a resource.
 */
public @interface ResourceInfo {

    String pluralName();

    /**
     * @return The uri path to this resource.
     */
    String pathElement();

    String[] commonImports() default {};
}
