package io.sphere.sdk.annotations;

public @interface HasQueryModel {
    String[] additionalContents() default {};

    String implBaseClass() default "";
}
