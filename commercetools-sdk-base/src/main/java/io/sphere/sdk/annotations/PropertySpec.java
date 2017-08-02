package io.sphere.sdk.annotations;


import org.apache.commons.lang3.StringUtils;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.CLASS)
public @interface PropertySpec {

    Class<?> fieldType();

    String name();

    boolean isOptional() default false;

    String jsonName() default "";

    boolean useReference() default false;

    String docLinkTaglet() default "";

}
