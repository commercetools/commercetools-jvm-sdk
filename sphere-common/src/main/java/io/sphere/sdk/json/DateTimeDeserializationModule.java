package io.sphere.sdk.json;

import com.fasterxml.jackson.databind.module.SimpleModule;

import java.time.ZonedDateTime;

final class DateTimeDeserializationModule extends SimpleModule {
    private static final long serialVersionUID = 0L;

    public DateTimeDeserializationModule() {
        addDeserializer(ZonedDateTime.class, new ZonedDateTimeDeserializer());
    }
}
