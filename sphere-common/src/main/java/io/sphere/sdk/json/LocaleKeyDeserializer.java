package io.sphere.sdk.json;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.KeyDeserializer;

import java.io.IOException;
import java.util.Locale;

final class LocaleKeyDeserializer extends KeyDeserializer {
    public LocaleKeyDeserializer() {
    }

    @Override
    public Object deserializeKey(final String key, final DeserializationContext ctxt) throws IOException, JsonProcessingException {
        return Locale.forLanguageTag(key);
    }
}
