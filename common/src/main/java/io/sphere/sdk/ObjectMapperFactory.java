package io.sphere.sdk;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.guava.GuavaModule;
import io.sphere.internal.util.json.Iso8601DateTimeJacksonModule;

final public class ObjectMapperFactory {
    private ObjectMapperFactory() {
    }

    public static ObjectMapper newObjectMapper() {
        return new ObjectMapper().registerModule(new GuavaModule()).registerModule(new Iso8601DateTimeJacksonModule());
    }
}
