package io.sphere.sdk.producttypes;

import io.sphere.sdk.attributes.AttributeDefinition;
import org.apache.commons.lang3.StringUtils;

import java.util.List;
import java.util.Optional;

public interface AttributeDefinitionContainer {
    List<AttributeDefinition> getAttributes();

    default AttributeDefinition getAttribute(final String attributeName) {
        return findAttribute(attributeName).orElse(null);
    }

    default Optional<AttributeDefinition> findAttribute(final String attributeName) {
        return getAttributes().stream()
                .filter(attribute -> StringUtils.equals(attributeName, attribute.getName()))
                .findAny();
    }
}
