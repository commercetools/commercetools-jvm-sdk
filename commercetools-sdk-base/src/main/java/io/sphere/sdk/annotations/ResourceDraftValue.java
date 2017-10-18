package io.sphere.sdk.annotations;

/**
 * This annotation marks an interface as resource draft representation.
 */
public @interface ResourceDraftValue {
    FactoryMethod[] factoryMethods();

    CopyFactoryMethod[] copyFactoryMethods() default {};

    /**
     * If set to true, the builder will return the concrete implementation.
     *
     * We switched the default to true because our previous generator always returned a concrete type,
     * although the builder type argument {@code T} was set to to the draft interface.
     *
     * @return true iff. the generated {@code build} method should the concrete implementation type.
     */
    boolean builderReturnsDslClass() default true;

    /**
     * Allows to specify additional interfaces that the generated builder should implement.
     *
     * @return list of fully qualified additional builder interface names
     */
    String[] additionalBuilderInterfaces() default {};

    /**
     * If set to true, the generated builder class will be abstract so that it can be further customized.
     *
     * @return true iff. the generated builder should be abstract
     */
    boolean abstractBuilderClass() default false;

    /**
     * If set to true, the generated resource value class will be abstract so that it can be further customized.
     *
     * @return true iff. the generated class should be abstract
     */
    boolean abstractResourceDraftValueClass() default false;
}
