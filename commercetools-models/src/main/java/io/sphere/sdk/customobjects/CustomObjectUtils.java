package io.sphere.sdk.customobjects;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.type.TypeFactory;

/**
 * Internal utility class.
 */
public final class CustomObjectUtils {
    private CustomObjectUtils() {
    }

    public static JavaType getCustomObjectJavaTypeForValue(final JavaType javaType) {
        final TypeFactory typeFactory = TypeFactory.defaultInstance();
        return typeFactory.constructParametricType(CustomObject.class, javaType);
    }
}
