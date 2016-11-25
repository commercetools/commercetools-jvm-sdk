package io.sphere.sdk.annotations;

public @interface HasCreateCommand {
    String javadocSummary() default "";
    String[] includeExamples() default {};
    String[] interfaceContents() default {};
}
