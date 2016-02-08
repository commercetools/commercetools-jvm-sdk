package io.sphere.sdk.json;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdScalarDeserializer;

import java.io.IOException;
import java.time.ZonedDateTime;

final class ZonedDateTimeDeserializer extends StdScalarDeserializer<ZonedDateTime> {
    private static final long serialVersionUID = 0L;

    public ZonedDateTimeDeserializer() {
        super(ZonedDateTime.class);
    }

    @Override
    public ZonedDateTime deserialize(final JsonParser jsonParser, final DeserializationContext deserializationContext) throws IOException {
        return ZonedDateTime.parse(jsonParser.getText());
    }
}
