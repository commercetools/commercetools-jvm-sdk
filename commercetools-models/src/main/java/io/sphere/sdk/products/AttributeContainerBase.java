package io.sphere.sdk.products;

import io.sphere.sdk.json.JsonException;
import io.sphere.sdk.models.Base;
import io.sphere.sdk.products.attributes.Attribute;
import io.sphere.sdk.products.attributes.AttributeMapper;
import io.sphere.sdk.products.attributes.NamedAttributeAccess;

import java.util.Objects;
import java.util.Optional;

import static java.lang.String.format;

abstract class AttributeContainerBase extends Base implements AttributeContainer {

    @Override
    public <T> Optional<T> findAttribute(final NamedAttributeAccess<T> accessor) {
        final String attributeName = accessor.getName();
        final Optional<Attribute> attributeOption = getAttributes().stream()
                .filter(a -> Objects.equals(attributeName, a.getName()))
                .findFirst();

        return attributeOption.map(attribute -> {
            final AttributeMapper<T> mapper = accessor.attributeMapper();

            try {
                return attribute.getValue(accessor);
            } catch (final JsonException e) {
                throw transformError(e, attributeName, mapper);
            }
        });
    }

    protected JsonException transformError(JsonException e, String attributeName, AttributeMapper<?> mapper) {
        return new JsonException(format("AttributeContainer does not contain an attribute '%s' which can be mapped with %s.", attributeName, mapper), e.getCause());
    }
}
