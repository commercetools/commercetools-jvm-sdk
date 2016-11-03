package io.sphere.sdk.annotations;

public @interface ResourceDraftValue {
    FactoryMethod[] factoryMethods() default {};

    String[] additionalDslClassContents() default {};

    String[] additionalBuilderClassContents() default {};
}
