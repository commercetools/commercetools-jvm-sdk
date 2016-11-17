package io.sphere.sdk.annotations;

public @interface HasQueryEndpoint {
    String[] additionalContents() default {};
}
