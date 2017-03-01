package io.sphere.sdk.annotations;

/**
 * This annotation marks an interface as resource draft representation.
 */
public @interface ResourceDraftValue {
    FactoryMethod[] factoryMethods();

    String[] additionalDslClassContents() default {};

    String additionalDslConstructorEndContent() default "";

    boolean gettersForBuilder() default false;

    boolean useBuilderStereotypeDslClass() default false;

    String[] additionalBuilderInterfaces() default {};

    /**
     * If set to true, the generated builder class will be abstract so that it can be further customized.
     *
     * @return true iff. the generated builder should be abstract
     */
    boolean abstractBuilderClass() default false;
}
