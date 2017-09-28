package io.sphere.sdk.projects;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdScalarDeserializer;

import java.io.IOException;
import java.time.ZonedDateTime;

final class TrialUntilDeserializer extends StdScalarDeserializer<ZonedDateTime> {
    private static final long serialVersionUID = 0L;

    public TrialUntilDeserializer() {
        super(ZonedDateTime.class);
    }

    @Override
    public ZonedDateTime deserialize(final JsonParser jsonParser, final DeserializationContext deserializationContext) throws IOException {
        final String value = deserializationContext.readValue(jsonParser, String.class);//value has the format "JJJJ-mm"
        final String correctedValue = value.matches("^\\d{4}-\\d{2}$") ? value + "-01T00:00:00.000Z" : value;
        return ZonedDateTime.parse(correctedValue);
    }
}
