package io.sphere.sdk.annotations;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.SOURCE)
public @interface QueryModelHint {
    String type();
    String impl() default "";
}
