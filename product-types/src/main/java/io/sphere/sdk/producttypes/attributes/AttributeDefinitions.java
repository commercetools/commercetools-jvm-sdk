package io.sphere.sdk.producttypes.attributes;

import org.apache.commons.lang3.StringUtils;

import java.util.List;
import java.util.Optional;

public final class AttributeDefinitions {
    private AttributeDefinitions() {
        //plain utility class
    }

    //TODO this really needs to be tested
    public static  <T extends AttributeDefinition> Optional<T> findByName(final List<AttributeDefinition> attributes, final String attributeName, final Class<T> clazz) {
        return attributes.stream().
                filter(attribute -> StringUtils.equals(attributeName, attribute.getName())).
                findAny().
                filter(elem -> clazz.isAssignableFrom(elem.getClass())).
                map(elem -> clazz.cast(elem));
    }
}
