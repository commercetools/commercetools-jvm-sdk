package io.sphere.sdk.attributes;

import io.sphere.sdk.json.JsonException;
import io.sphere.sdk.models.Base;
import io.sphere.sdk.products.Product;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class AttributeContainerImpl extends Base implements AttributeContainer {
    private final List<Attribute> attributes;

    protected AttributeContainerImpl(List<Attribute> attributes) {
        this.attributes = attributes;
    }

    @Override
    public List<Attribute> getAttributes() {
        return attributes;
    }

    @Override
    public <T> Optional<T> getAttribute(final AttributeGetter<Product, T> accessor) {
        final String attributeName = accessor.getName();
        final Optional<Attribute> attributeOption = getAttributes().stream()
                .filter(a -> Objects.equals(attributeName, a.getName()))
                .findFirst();

        return attributeOption.map(attribute -> {
            final AttributeMapper<T> mapper = accessor.getMapper();

            try {
                return attribute.getValue(mapper);
            } catch (final JsonException e) {
                throw transformError(e, attributeName, mapper);
            }
        });
    }

    protected JsonException transformError(JsonException e, String attributeName, AttributeMapper<?> mapper) {
//        throw new JsonException(format("TODO: %s does not contain an attribute '%s' which can be mapped with %s.", objectWithAttributes, attributeName, mapper), cause);
        // TODO: implement proper error handling
        return e;
    }

    public static AttributeContainerImpl of(List<Attribute> attributes) {
        return new AttributeContainerImpl(attributes);
    }
}
