package io.sphere.sdk.json;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdScalarSerializer;

import java.io.IOException;
import java.time.Instant;
import java.time.format.DateTimeFormatter;

final class InstantSerializer extends StdScalarSerializer<Instant> {
    static final long serialVersionUID = 0L;

    public InstantSerializer() {
        super(Instant.class);
    }

    @Override
    public void serialize(Instant value, JsonGenerator gen, SerializerProvider arg2) throws IOException {
        gen.writeString(DateTimeFormatter.ISO_INSTANT.format(value));
    }
}
