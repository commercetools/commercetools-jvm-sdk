package io.sphere.sdk.producttypes;

import io.sphere.sdk.attributes.AttributeDefinition;
import org.apache.commons.lang3.StringUtils;

import java.util.List;
import java.util.Optional;

public interface AttributeDefinitionContainer {
    List<AttributeDefinition> getAttributes();

    default <T extends AttributeDefinition> Optional<T> getAttribute(final String attributeName, final Class<T> clazz) {
        return getAttributes().stream().
                filter(attribute -> StringUtils.equals(attributeName, attribute.getName())).
                findAny().
                filter(elem -> clazz.isAssignableFrom(elem.getClass())).
                map(elem -> clazz.cast(elem));
    }

    default Optional<AttributeDefinition> getAttribute(final String attributeName) {
        return getAttributes().stream().
                filter(attribute -> StringUtils.equals(attributeName, attribute.getName())).
                findAny();
    }
}
