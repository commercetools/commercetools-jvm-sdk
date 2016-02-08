package io.sphere.sdk.json;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;
import java.util.Locale;

final class LocaleKeySerializer extends JsonSerializer<Locale> {
    public LocaleKeySerializer() {
    }

    @Override
    public void serialize(final Locale locale, final JsonGenerator jsonGenerator, final SerializerProvider serializers) throws IOException, JsonProcessingException {
        jsonGenerator.writeFieldName(locale.toLanguageTag());
    }
}
