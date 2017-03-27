package io.sphere.sdk.annotations;

/**
 * This annotation enables the generation of a builder for the annotated interface.
 */
public @interface HasBuilder {
    FactoryMethod[] factoryMethods();

    /**
     * If set to true, the generated builder will also provide getter methods.
     *
     * @return true iff. getter methods should be generated for the builder
     */
    boolean gettersForBuilder() default false;

    /**
     * The prefix used to derived the name of the implemetation class of the annotated type.
     *
     * @return the implementation class prefix.
     */
    String implPrefix() default "Impl";
}
