package io.sphere.sdk.annotations;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.SOURCE)
public @interface HasQueryModel {
    String[] additionalContents() default {};

    String implBaseClass() default "";

    String[] baseInterfaces() default {};
}
