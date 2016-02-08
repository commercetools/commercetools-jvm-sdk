package io.sphere.sdk.json;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdScalarSerializer;

import java.io.IOException;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

final class LocalTimeSerializer extends StdScalarSerializer<LocalTime> {
    static final long serialVersionUID = 0L;

    public LocalTimeSerializer() {
        super(LocalTime.class);
    }

    @Override
    public void serialize(LocalTime value, JsonGenerator gen, SerializerProvider arg2) throws IOException {
        gen.writeString(DateTimeFormatter.ISO_TIME.format(value));
    }
}
