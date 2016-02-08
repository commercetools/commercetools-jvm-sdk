package io.sphere.sdk.products.attributes;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.sphere.sdk.json.JsonException;
import io.sphere.sdk.models.Base;

import static java.lang.String.format;

final class AttributeImpl extends Base implements Attribute {
    private final String name;

    @JsonSerialize
    private final JsonNode value;

    @JsonCreator
    public AttributeImpl(final String name, final JsonNode value) {
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    @Override
    public <T> T getValue(final AttributeAccess<T> access) {
        try {
            return access.attributeMapper().deserialize(value);
        } catch (final JsonException e) {
            throw new JsonException(format("Cannot parse attribute %s with mapper %s.", getName(), access.attributeMapper()), e.getCause());
        }
    }
}
