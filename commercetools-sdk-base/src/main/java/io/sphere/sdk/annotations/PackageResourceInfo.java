package io.sphere.sdk.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * This annotation specifies which resource this package contains. This information can then be used
 * to derive the correct resource type for our update action generator when a type different from
 * the resource type is annotated.
 */
@Target(ElementType.PACKAGE)
@Retention(RetentionPolicy.SOURCE)
public @interface PackageResourceInfo {
    Class<?> type();
}
