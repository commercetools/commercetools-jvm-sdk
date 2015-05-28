package io.sphere.sdk.json;

import org.junit.Test;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

import static org.assertj.core.api.Assertions.*;

public class JsonUtilsTest {
    @Test
    public void serializeZonedDateTime() throws Exception {
        final String timeAsString = "2007-12-03T10:15:30+02:00[Europe/Berlin]";
        final ZonedDateTime dateTime = ZonedDateTime.parse(timeAsString);
        final String actual = JsonUtils.toJson(dateTime);
        assertThat(actual).isEqualTo("\"2007-12-03T09:15:30Z\"");
    }

    @Test
    public void deserializeZonedDateTime() throws Exception {
        final String timeAsString = "2001-09-11T14:00:00.000Z";
        final ZonedDateTime actual = JsonUtils.readObject(TypeReferences.ZonedDateTimeTypeReference(), ("\"" + timeAsString + "\"").getBytes());
        assertThat(DateTimeFormatter.ISO_INSTANT.format(actual)).isEqualTo("2001-09-11T14:00:00Z");
    }
}