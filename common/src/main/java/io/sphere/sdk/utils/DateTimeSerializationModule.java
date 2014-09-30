package io.sphere.sdk.utils;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.ser.std.StdScalarSerializer;

import java.io.IOException;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

class DateTimeSerializationModule extends SimpleModule {
    private static final long serialVersionUID = 0L;

    public DateTimeSerializationModule() {
        addSerializer(Instant.class, new InstantSerializer());
        addSerializer(LocalDate.class, new LocalDateSerializer());
        addSerializer(LocalTime.class, new LocalTimeSerializer());
    }

    private static final class InstantSerializer extends StdScalarSerializer<Instant> {

        public InstantSerializer() {
            super(Instant.class);
        }

        @Override
        public void serialize(Instant value, JsonGenerator gen, SerializerProvider arg2) throws IOException {
            gen.writeString(DateTimeFormatter.ISO_INSTANT.format(value));
        }
    }

    private static final class LocalDateSerializer extends StdScalarSerializer<LocalDate> {

        public LocalDateSerializer() {
            super(LocalDate.class);
        }

        @Override
        public void serialize(LocalDate value, JsonGenerator gen, SerializerProvider arg2) throws IOException {
            gen.writeString(DateTimeFormatter.ISO_DATE.format(value));
        }
    }

    private static final class LocalTimeSerializer extends StdScalarSerializer<LocalTime> {

        public LocalTimeSerializer() {
            super(LocalTime.class);
        }

        @Override
        public void serialize(LocalTime value, JsonGenerator gen, SerializerProvider arg2) throws IOException {
            gen.writeString(DateTimeFormatter.ISO_TIME.format(value));
        }
    }
}
