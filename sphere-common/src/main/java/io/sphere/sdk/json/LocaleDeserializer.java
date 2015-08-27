package io.sphere.sdk.json;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import java.io.IOException;
import java.util.Locale;

final class LocaleDeserializer extends JsonDeserializer<Locale> {
    private static final long serialVersionUID = 0L;

    @Override
    public Locale deserialize(final JsonParser jsonParser, final DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
        final String languageTag = deserializationContext.readValue(jsonParser, String.class);
        return Locale.forLanguageTag(languageTag);
    }
}
