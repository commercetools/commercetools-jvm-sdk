package io.sphere.sdk.annotations;

public @interface FactoryMethod {
    String methodName() default "of";

    String[] parameterNames();
}
