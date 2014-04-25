package io.sphere.internal.util.json;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

public final class SphereObjectMapperFactory {
    private SphereObjectMapperFactory() {
    }

    public static ObjectMapper newObjectMapper() {
        return (new ObjectMapper()).
                registerModule(new Iso8601DateTimeJacksonModule()).
                configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }
}
