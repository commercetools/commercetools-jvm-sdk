package io.sphere.sdk.json;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import io.sphere.sdk.models.LocalizedString;
import org.junit.Test;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

import static java.util.Locale.*;
import static org.assertj.core.api.Assertions.*;

public class SphereJsonUtilsTest {
    @Test
    public void serializeZonedDateTime() throws Exception {
        final String timeAsString = "2007-12-03T10:15:30+02:00[Europe/Berlin]";
        final ZonedDateTime dateTime = ZonedDateTime.parse(timeAsString);
        final String actual = SphereJsonUtils.toJsonString(dateTime);
        assertThat(actual).isEqualTo("\"2007-12-03T09:15:30.000Z\"");
    }

    @Test
    public void deserializeZonedDateTime() throws Exception {
        final String timeAsString = "2001-09-11T14:00:00.000Z";
        final ZonedDateTime actual = SphereJsonUtils.readObject(("\"" + timeAsString + "\"").getBytes(), TypeReferences.zonedDateTimeTypeReference());
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
        final LocalizedString value = LocalizedString.of(ENGLISH, "dog food", GERMAN, "Hundefutter");
        assertThat(SphereJsonUtils.toJsonString(value))
                .isEqualTo("{\"en\":\"dog food\",\"de\":\"Hundefutter\"}");
    }

    @Test
    public void toPrettyJsonString() throws Exception {
        final LocalizedString value = LocalizedString.of(ENGLISH, "dog food", GERMAN, "Hundefutter");
        assertThat(SphereJsonUtils.toPrettyJsonString(value))
                .isEqualTo("{\n" +
                        "  \"en\" : \"dog food\",\n" +
                        "  \"de\" : \"Hundefutter\"\n" +
                        "}");
    }

    @Test
    public void readObjectFromJsonString() throws Exception {
        final String jsonString = "{\"de\":\"Hundefutter\",\"en\":\"dog food\"}";
        final LocalizedString actual =
                SphereJsonUtils.readObject(jsonString, LocalizedString.typeReference());
        assertThat(actual).isEqualTo(LocalizedString.of(ENGLISH, "dog food", GERMAN, "Hundefutter"));
    }

    @Test
    public void readObjectFromJsonNode() throws Exception {
        final ObjectNode jsonNode = SphereJsonUtils.newObjectNode();
        jsonNode.put("de", "Hundefutter");
        jsonNode.put("en", "dog food");
        final LocalizedString actual =
                SphereJsonUtils.readObject(jsonNode, LocalizedString.typeReference());
        assertThat(actual).isEqualTo(LocalizedString.of(ENGLISH, "dog food", GERMAN, "Hundefutter"));
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
        final LocalizedString actual =
                SphereJsonUtils.readObject(jsonNode, LocalizedString.class);
        assertThat(actual).isEqualTo(LocalizedString.of(ENGLISH, "dog food", GERMAN, "Hundefutter"));
    }

    @Test
    public void toJsonNode() throws Exception {
        final LocalizedString value = LocalizedString.of(ENGLISH, "dog food", GERMAN, "Hundefutter");
        final JsonNode actual = SphereJsonUtils.toJsonNode(value);
        assertThat(actual.get("de").asText()).isEqualTo("Hundefutter");
        assertThat(actual.get("en").asText()).isEqualTo("dog food");
    }

    @Test
    public void exceptionHandling() throws Exception {
        final String brokenJsonString = "{\"de\":\",]]]]";
        assertThatThrownBy(() -> SphereJsonUtils.readObject(brokenJsonString, LocalizedString.typeReference()))
        .isInstanceOf(JsonException.class);
    }

    @Test
    public void serializeLocale() {
        final String jsonString = SphereJsonUtils.toJsonString(Locale.ENGLISH);
        assertThat(jsonString).isEqualTo("\"en\"");
    }

    @Test
    public void deserializelLocale() {
        final String jsonString = "\"en\"";
        final Locale locale = SphereJsonUtils.readObject(jsonString, TypeReferences.localeTypeReference());
        assertThat(locale).isEqualTo(Locale.ENGLISH);
    }

    @Test
    public void serializeFullLocale() {
        final String jsonString = SphereJsonUtils.toJsonString(Locale.US);
        assertThat(jsonString).isEqualTo("\"en-US\"");
    }

    @Test
    public void deserializeFullLocale() {
        final String jsonString = "\"en-US\"";
        final Locale locale = SphereJsonUtils.readObject(jsonString, TypeReferences.localeTypeReference());
        assertThat(locale).isEqualTo(Locale.US);
    }

    @Test
    public void doNotFailOnEmptyBeans() {
        SphereJsonUtils.toJsonNode(new EmptyBean());
    }

    private static class EmptyBean {

    }
}