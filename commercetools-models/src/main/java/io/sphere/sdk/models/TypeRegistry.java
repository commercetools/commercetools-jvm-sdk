package io.sphere.sdk.models;

import com.fasterxml.jackson.annotation.JsonTypeName;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * This interface provides access to the type name of our resources.
 *
 * The implementation is generated from our resource value interfaces that
 * are annotated with {@link JsonTypeName}.
 */
public interface TypeRegistry {
    /**
     * Returns the class of the given type.
     *
     * @param type the type
     *
     * @return class of the given type
     */
    Class<?> toClass(String type);

    /**
     * Returns the classes of the given type names.
     *
     * @param types the types
     *
     * @return the classes of the given types
     */
    default List<Class<?>> toClasses(List<String> types) {
        List<Class<?>> collect = types.stream()
                .map(this::toClass)
                .collect(Collectors.toList());
        return collect;
    }

    /**
     * Returns the type of the given class.
     *
     * @param clazz
     * @return
     */
    String toType(Class<?> clazz);

    /**
     * Returns the types of the given classes.
     *
     * @param classes the classes
     *
     * @return the types of the given types
     */
    default List<String> toTypes(Class<?>... classes) {
        return Arrays.asList(classes).stream()
                .map(this::toType)
                .collect(Collectors.toList());
    }

    /**
     * Creates a new type registry.
     *
     * @return new type registry
     */
    static TypeRegistry of() {
        return TypeRegistryImpl.INSTANCE;
    }
}
