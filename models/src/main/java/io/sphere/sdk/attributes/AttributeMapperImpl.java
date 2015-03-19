package io.sphere.sdk.attributes;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.sphere.sdk.json.JsonException;
import io.sphere.sdk.models.Base;
import io.sphere.sdk.json.JsonUtils;

import java.io.IOException;

class AttributeMapperImpl<T> extends Base implements AttributeMapper<T> {
    private static final ObjectMapper mapper = JsonUtils.newObjectMapper();
    private final TypeReference<T> typeReference;

    AttributeMapperImpl(final TypeReference<T> typeReference) {
        this.typeReference = typeReference;
    }

    @Override
    public T deserialize(final JsonNode value) {
        try {
            return  mapper.reader(typeReference).readValue(value);
        } catch (final IOException e) {
            throw new JsonException(e);
        }
    }

    @Override
    public JsonNode serialize(final T value) {
        return mapper.valueToTree(value);
    }

    protected final ObjectMapper mapper() {
        return mapper;
    }
}
