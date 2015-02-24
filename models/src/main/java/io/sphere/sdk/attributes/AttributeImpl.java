package io.sphere.sdk.attributes;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.sphere.sdk.errors.JsonException;
import io.sphere.sdk.models.Base;

import static java.lang.String.format;

class AttributeImpl extends Base implements Attribute {
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
    public <T> T getValue(final AttributeMapper<T> mapper) {
        try {
            return mapper.deserialize(value);
        } catch (final JsonException e) {
            throw new JsonException(format("Cannot parse attribute %s with mapper %s.", getName(), mapper), e.getCause());
        }
    }

    @Override
    public JsonNode valueAsJson() {
        return value;
    }
}
