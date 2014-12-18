package io.sphere.sdk.utils;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.ser.std.StdScalarSerializer;
import io.sphere.sdk.models.SphereEnumeration;

import java.io.IOException;

final class SphereEnumModule extends SimpleModule {
    private static final long serialVersionUID = 0L;

    SphereEnumModule() {
        addSerializer(SphereEnumeration.class, new EnumSerializer());
    }

    private static class EnumSerializer extends StdScalarSerializer<SphereEnumeration> {
        private static final long serialVersionUID = 0L;

        EnumSerializer() {
            super(SphereEnumeration.class);
        }

        @Override
        public void serialize(final SphereEnumeration sphereEnumeration, final JsonGenerator jsonGenerator, final SerializerProvider serializerProvider) throws IOException {
            final String sphereName = SphereEnumeration.toSphereName(sphereEnumeration.name());
            jsonGenerator.writeString(sphereName);
        }
    }

}
