package io.sphere.sdk.annotations;

public @interface FactoryMethod {
    /**
     * The name of this static factory method.
     *
     * @return factory method name
     */
    String methodName() default "of";

    /**
     * The parameter names or none for the factory method.
     * The parameter names must have corresponding json properties in the annotated interface.
     *
     * @return the parameter names
     */
    String[] parameterNames();

    boolean useLowercaseBooleans() default false;
}
