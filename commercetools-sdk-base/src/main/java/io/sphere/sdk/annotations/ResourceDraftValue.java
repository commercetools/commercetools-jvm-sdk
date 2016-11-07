package io.sphere.sdk.annotations;

public @interface ResourceDraftValue {
    FactoryMethod[] factoryMethods();

    String[] additionalDslClassContents() default {};

    String[] additionalBuilderClassContents() default {};

    boolean gettersForBuilder() default false;
}
