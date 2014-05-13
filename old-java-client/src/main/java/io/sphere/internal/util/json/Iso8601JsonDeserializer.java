package io.sphere.internal.util.json;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdScalarDeserializer;
import org.joda.time.DateTime;
import org.joda.time.format.ISODateTimeFormat;

import java.io.IOException;

class Iso8601JsonDeserializer extends StdScalarDeserializer<DateTime> {
    public Iso8601JsonDeserializer() { super(DateTime.class); }

    @Override
    public DateTime deserialize(JsonParser jsonParser, DeserializationContext deserializationContext)
            throws IOException {
        final String valueAsString = jsonParser.getValueAsString();
        return ISODateTimeFormat.dateTime().parseDateTime(valueAsString);
    }
}