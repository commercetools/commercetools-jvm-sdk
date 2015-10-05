package io.sphere.sdk.customobjects;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.type.TypeFactory;

/**
 * Internal utility class.
 */
public final class CustomObjectUtils {
    private CustomObjectUtils() {
    }

    public static<T> JavaType getCustomObjectJavaTypeForValue(final TypeReference<T> typeReference) {
        final TypeFactory typeFactory = TypeFactory.defaultInstance();
        final JavaType typeParameterJavaType = typeFactory.constructType(typeReference);
        return typeFactory.constructParametrizedType(CustomObject.class, CustomObject.class, typeParameterJavaType);
    }

    public static<T> JavaType getCustomObjectJavaTypeForValue(final JavaType javaType) {
        final TypeFactory typeFactory = TypeFactory.defaultInstance();
        return typeFactory.constructParametrizedType(CustomObject.class, CustomObject.class, javaType);
    }
}
