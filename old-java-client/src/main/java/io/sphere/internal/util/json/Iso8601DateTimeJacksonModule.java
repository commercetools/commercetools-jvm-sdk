package io.sphere.internal.util.json;

import com.fasterxml.jackson.databind.module.SimpleModule;
import org.joda.time.DateTime;

public final class Iso8601DateTimeJacksonModule extends SimpleModule {
    public Iso8601DateTimeJacksonModule() {
        addSerializer(DateTime.class, new Iso8601JsonSerializer());
        addDeserializer(DateTime.class, new Iso8601JsonDeserializer());
    }
}
