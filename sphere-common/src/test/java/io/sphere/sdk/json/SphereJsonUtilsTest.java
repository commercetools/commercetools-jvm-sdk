package io.sphere.sdk.json;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import io.sphere.sdk.models.LocalizedStrings;
import org.junit.Test;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

import static java.util.Locale.*;
import static org.assertj.core.api.Assertions.*;

public class SphereJsonUtilsTest {
    @Test
    public void serializeZonedDateTime() throws Exception {
        final String timeAsString = "2007-12-03T10:15:30+02:00[Europe/Berlin]";
        final ZonedDateTime dateTime = ZonedDateTime.parse(timeAsString);
        final String actual = SphereJsonUtils.toJsonString(dateTime);
        assertThat(actual).isEqualTo("\"2007-12-03T09:15:30Z\"");
    }

    @Test
    public void deserializeZonedDateTime() throws Exception {
        final String timeAsString = "2001-09-11T14:00:00.000Z";
        final ZonedDateTime actual = SphereJsonUtils.readObject(("\"" + timeAsString + "\"").getBytes(), TypeReferences.ZonedDateTimeTypeReference());
        assertThat(DateTimeFormatter.ISO_INSTANT.format(actual)).isEqualTo("2001-09-11T14:00:00Z");
    }

    @Test
    public void prettyPrint() throws Exception {
        final String jsonString = "{\"de\":\"Hundefutter\",\"en\":\"dog food\"}";
        final String actual = SphereJsonUtils.prettyPrint(jsonString);
        assertThat(actual).isEqualTo("{\n" +
                        "  \"de\" : \"Hundefutter\",\n" +
                        "  \"en\" : \"dog food\"\n" +
                        "}"
        );
    }

    @Test
    public void toJsonString() throws Exception {
        final LocalizedStrings value = LocalizedStrings.of(ENGLISH, "dog food", GERMAN, "Hundefutter");
        final String actual = SphereJsonUtils.toJsonString(value);
        assertThat(actual).isEqualTo("{\"de\":\"Hundefutter\",\"en\":\"dog food\"}");
    }

    @Test
    public void readObjectFromJsonString() throws Exception {
        final String jsonString = "{\"de\":\"Hundefutter\",\"en\":\"dog food\"}";
        final LocalizedStrings actual =
                SphereJsonUtils.readObject(jsonString, LocalizedStrings.typeReference());
        assertThat(actual).isEqualTo(LocalizedStrings.of(ENGLISH, "dog food", GERMAN, "Hundefutter"));
    }

    @Test
    public void readObjectFromJsonNode() throws Exception {
        final ObjectNode jsonNode = SphereJsonUtils.newObjectNode();
        jsonNode.put("de", "Hundefutter");
        jsonNode.put("en", "dog food");
        final LocalizedStrings actual =
                SphereJsonUtils.readObject(jsonNode, LocalizedStrings.typeReference());
        assertThat(actual).isEqualTo(LocalizedStrings.of(ENGLISH, "dog food", GERMAN, "Hundefutter"));
    }

    @Test
    public void parse() throws Exception {
        final JsonNode actual =
                SphereJsonUtils.parse("{\"de\":\"Hundefutter\",\"en\":\"dog food\"}");
        final ObjectNode expected = SphereJsonUtils.newObjectNode();
        expected.put("de", "Hundefutter");
        expected.put("en", "dog food");
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    public void readObjectFromJsonNodeWithClass() throws Exception {
        final ObjectNode jsonNode = SphereJsonUtils.newObjectNode();
        jsonNode.put("de", "Hundefutter");
        jsonNode.put("en", "dog food");
        final LocalizedStrings actual =
                SphereJsonUtils.readObject(jsonNode, LocalizedStrings.class);
        assertThat(actual).isEqualTo(LocalizedStrings.of(ENGLISH, "dog food", GERMAN, "Hundefutter"));
    }

    @Test
    public void toJsonNode() throws Exception {
        final LocalizedStrings value = LocalizedStrings.of(ENGLISH, "dog food", GERMAN, "Hundefutter");
        final JsonNode actual = SphereJsonUtils.toJsonNode(value);
        assertThat(actual.get("de").asText()).isEqualTo("Hundefutter");
        assertThat(actual.get("en").asText()).isEqualTo("dog food");
    }

    @Test
    public void exceptionHandling() throws Exception {
        final String brokenJsonString = "{\"de\":\",]]]]";
        assertThatThrownBy(() -> SphereJsonUtils.readObject(brokenJsonString, LocalizedStrings.typeReference()))
        .isInstanceOf(JsonException.class);
    }
}