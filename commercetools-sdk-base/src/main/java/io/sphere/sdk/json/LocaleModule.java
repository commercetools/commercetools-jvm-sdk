package io.sphere.sdk.json;

import com.fasterxml.jackson.databind.module.SimpleModule;

import java.util.Locale;

final class LocaleModule extends SimpleModule {
    private static final long serialVersionUID = 0L;

    LocaleModule() {
        addSerializer(Locale.class, new LocaleSerializer());
        addDeserializer(Locale.class, new LocaleDeserializer());
        addKeyDeserializer(Locale.class, new LocaleKeyDeserializer());
        addKeySerializer(Locale.class, new LocaleKeySerializer());
    }
}
