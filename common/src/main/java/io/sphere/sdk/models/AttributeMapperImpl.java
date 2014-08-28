package io.sphere.sdk.models;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.sphere.sdk.models.exceptions.AttributeMappingException;
import io.sphere.sdk.models.exceptions.JsonParseException;
import io.sphere.sdk.utils.JsonUtils;

import java.io.IOException;

import static java.lang.String.format;

class AttributeMapperImpl<T> extends Base implements AttributeMapper<T> {
    private static final ObjectMapper mapper = JsonUtils.newObjectMapper();
    private final TypeReference<T> typeReference;

    AttributeMapperImpl(final TypeReference<T> typeReference) {
        this.typeReference = typeReference;
    }

    @Override
    public T parse(final JsonNode value) {
        try {
            return  mapper.reader(typeReference).readValue(value);
        } catch (final JsonMappingException e) {
            throw new AttributeMappingException(e);
        } catch (final IOException e) {
            throw new JsonParseException(e);
        }
    }
}
