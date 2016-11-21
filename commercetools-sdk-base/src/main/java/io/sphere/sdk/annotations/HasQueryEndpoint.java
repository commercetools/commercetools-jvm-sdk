package io.sphere.sdk.annotations;

public @interface HasQueryEndpoint {
    String[] additionalContentsQueryInterface() default {};
}
