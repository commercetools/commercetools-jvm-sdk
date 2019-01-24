package io.sphere.sdk.products.attributes;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import io.sphere.sdk.models.Reference;

import java.io.IOException;

final class ReferenceInternalSerializer extends StdSerializer<Reference> {

    ReferenceInternalSerializer(){
        super(Reference.class,true);
    }

    public Class<Reference> handledType() {
        return super.handledType();
    }

    @Override
    public void serialize(final Reference reference, final JsonGenerator jsonGenerator, final SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeStartObject();
        jsonGenerator.writeStringField("id", reference.getId());
        jsonGenerator.writeStringField("typeId", reference.getTypeId());
        jsonGenerator.writeObjectField("obj", reference.getObj());
        jsonGenerator.writeEndObject();
    }
}
