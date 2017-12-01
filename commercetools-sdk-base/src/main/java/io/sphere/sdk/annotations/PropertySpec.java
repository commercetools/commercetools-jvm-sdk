package io.sphere.sdk.annotations;


import org.apache.commons.lang3.StringUtils;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.CLASS)
public @interface PropertySpec {
    /**
     * The type for the field. An array type will be converted to a paramaterized list type.
     *
     * @return the type of this property
     */
    Class<?> type();

    String name();

    boolean isOptional() default false;

    String jsonName() default "";

    boolean useReference() default false;

    String docLinkTaglet() default "";

}
