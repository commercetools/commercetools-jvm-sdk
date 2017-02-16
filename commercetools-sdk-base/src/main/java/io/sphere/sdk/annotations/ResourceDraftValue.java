package io.sphere.sdk.annotations;

/**
 * This annotation marks an interface as resource draft representation.
 */
public @interface ResourceDraftValue {
    FactoryMethod[] factoryMethods();

    String[] additionalDslClassContents() default {};

    String[] additionalBuilderClassContents() default {};

    String additionalDslConstructorEndContent() default "";

    boolean gettersForBuilder() default false;

    boolean useBuilderStereotypeDslClass() default false;

    String[] additionalBuilderInterfaces() default {};
}
