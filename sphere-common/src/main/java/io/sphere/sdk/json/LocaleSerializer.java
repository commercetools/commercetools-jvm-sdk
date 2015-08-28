package io.sphere.sdk.json;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdScalarSerializer;

import java.io.IOException;
import java.util.Locale;

final class LocaleSerializer extends StdScalarSerializer<Locale> {
    static final long serialVersionUID = 0L;

    public LocaleSerializer() {
        super(Locale.class);
    }

    @Override
    public void serialize(final Locale locale, final JsonGenerator jsonGenerator, final SerializerProvider provider) throws IOException {
        jsonGenerator.writeString(locale.toLanguageTag());
    }
}
