package io.sphere.sdk.annotations;

public @interface HasUpdateCommand {
    String javadocSummary() default "";
    String[] includeExamples() default {};
    boolean updateWithKey() default false;
}
