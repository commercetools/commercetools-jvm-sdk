package io.sphere.sdk.annotations;

public @interface HasDeleteCommand {
    String javadocSummary() default "";
    String[] includeExamples() default {};
}
