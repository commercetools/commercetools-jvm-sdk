package io.sphere.sdk.annotations;

import java.io.Serializable;

public @interface HasByIdGetEndpoint {
    String javadocSummary() default "";
    String[] includeExamples() default {};
}
