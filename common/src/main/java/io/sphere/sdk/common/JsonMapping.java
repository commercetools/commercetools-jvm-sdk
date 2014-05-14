package io.sphere.sdk.common;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.guava.GuavaModule;
import io.sphere.internal.util.json.Iso8601DateTimeJacksonModule;

final public class JsonMapping {
    private static ObjectMapper objectMapper = newObjectMapper();

    private JsonMapping() {
    }

    public static ObjectMapper newObjectMapper() {
        return new ObjectMapper().registerModule(new GuavaModule()).registerModule(new Iso8601DateTimeJacksonModule());
    }

    public static String toJson(final Object object) {
        try {
            return objectMapper.writeValueAsString(object);
        } catch (final JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
