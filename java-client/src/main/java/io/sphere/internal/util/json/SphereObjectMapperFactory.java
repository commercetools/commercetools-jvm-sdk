package io.sphere.internal.util.json;

import com.fasterxml.jackson.databind.ObjectMapper;

public final class SphereObjectMapperFactory {
    private SphereObjectMapperFactory() {
    }

    public static ObjectMapper newObjectMapper() {
        final ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new Iso8601DateTimeJacksonModule());
        return mapper;
    }
}
