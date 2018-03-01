package io.sphere.sdk.annotations;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.SOURCE)
public @interface HasUpdateCommand {
    String javadocSummary() default "";
    String[] includeExamples() default {};
    String[] updateWith() default {};
}
