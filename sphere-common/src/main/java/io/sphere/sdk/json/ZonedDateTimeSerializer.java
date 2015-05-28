package io.sphere.sdk.json;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdScalarSerializer;

import java.io.IOException;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

final class ZonedDateTimeSerializer extends StdScalarSerializer<ZonedDateTime> {
    static final long serialVersionUID = 0L;

    public ZonedDateTimeSerializer() {
        super(ZonedDateTime.class);
    }

    @Override
    public void serialize(ZonedDateTime value, JsonGenerator gen, SerializerProvider arg2) throws IOException {
        final String res = DateTimeFormatter.ISO_INSTANT.format(value.withZoneSameInstant(ZoneId.of("Z")));
        gen.writeString(res);
    }
}
