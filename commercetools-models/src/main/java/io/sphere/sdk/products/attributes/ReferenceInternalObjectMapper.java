package io.sphere.sdk.products.attributes;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import io.sphere.sdk.json.SphereJsonUtils;

final class ReferenceInternalObjectMapper {

    private static final ObjectMapper instance;

    static {
        instance = SphereJsonUtils.newObjectMapper();
        final SimpleModule module = new SimpleModule();
        module.addSerializer(new ReferenceInternalSerializer());
        instance.registerModule(module);
    }

    static ObjectMapper getInstance(){
        return instance;
    }
}