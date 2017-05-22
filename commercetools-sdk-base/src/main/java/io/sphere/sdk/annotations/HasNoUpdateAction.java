package io.sphere.sdk.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.SOURCE)
@Target({ElementType.METHOD})
/**
 * This annotation marks methods that will be ignored while generating update actions with {@link HasUpdateActions}
 */
public @interface HasNoUpdateAction {
}
