package io.sphere.sdk.models;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.guava.GuavaModule;

final class ObjectMapperFactory {
    private ObjectMapperFactory() {
    }

    public static ObjectMapper newObjectMapper() {
        return new ObjectMapper().registerModule(new GuavaModule());
    }
}
