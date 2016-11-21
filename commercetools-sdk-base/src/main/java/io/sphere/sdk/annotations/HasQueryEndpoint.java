package io.sphere.sdk.annotations;

public @interface HasQueryEndpoint {
    String[] additionalContentsQueryImpl() default {};
}
