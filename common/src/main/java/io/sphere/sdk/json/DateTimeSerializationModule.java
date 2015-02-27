package io.sphere.sdk.json;

import com.fasterxml.jackson.databind.module.SimpleModule;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalTime;

final class DateTimeSerializationModule extends SimpleModule {
    private static final long serialVersionUID = 0L;

    public DateTimeSerializationModule() {
        addSerializer(Instant.class, new InstantSerializer());
        addSerializer(LocalDate.class, new LocalDateSerializer());
        addSerializer(LocalTime.class, new LocalTimeSerializer());
    }
}
