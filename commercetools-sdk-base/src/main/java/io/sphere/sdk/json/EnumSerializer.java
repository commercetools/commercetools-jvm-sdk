package io.sphere.sdk.json;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdScalarSerializer;
import io.sphere.sdk.models.SphereEnumeration;

import java.io.IOException;

class EnumSerializer extends StdScalarSerializer<SphereEnumeration> {
    private static final long serialVersionUID = 0L;

    EnumSerializer() {
        super(SphereEnumeration.class);
    }

    @Override
    public void serialize(final SphereEnumeration sphereEnumeration, final JsonGenerator jsonGenerator, final SerializerProvider serializerProvider) throws IOException {
        final String sphereName = sphereEnumeration.toSphereName();
        jsonGenerator.writeString(sphereName);
    }
}
