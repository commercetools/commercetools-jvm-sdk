package io.sphere.sdk.products;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.sphere.sdk.models.Base;
import io.sphere.sdk.models.exceptions.JsonParseException;

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
        } catch (final JsonParseException e) {
            throw new JsonParseException(format("Cannot parse attribute %s with mapper %s.", getName(), mapper), e.getCause());
        }
    }
}
