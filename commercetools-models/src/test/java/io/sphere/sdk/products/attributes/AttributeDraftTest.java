package io.sphere.sdk.products.attributes;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.sphere.sdk.models.EnumValue;
import io.sphere.sdk.models.LocalizedEnumValue;
import io.sphere.sdk.models.LocalizedString;
import io.sphere.sdk.models.Reference;
import org.junit.Test;

import java.util.Locale;
import java.util.Set;

import static io.sphere.sdk.utils.SphereInternalUtils.asSet;
import static java.util.Locale.ENGLISH;
import static java.util.Locale.GERMAN;
import static org.assertj.core.api.Assertions.assertThat;

public class AttributeDraftTest {
    private final LocalizedEnumValue green = LocalizedEnumValue.of("green",
            LocalizedString.of(ENGLISH, "green").plus(GERMAN, "grün"));
    private final LocalizedEnumValue red = LocalizedEnumValue.of("red",
            LocalizedString.of(ENGLISH, "red").plus(GERMAN, "rot"));
    private final EnumValue s = EnumValue.of("S", "S");
    private final EnumValue m = EnumValue.of("M", "M");
    private final ObjectMapper objectMapper = new ObjectMapper();


    @Test
    public void usesForLocalizedEnumJustKey() throws Exception {
        final AttributeDraft draft = AttributeDraft.of("foo", green);
        assertThat(draft.getValue().asText()).isEqualTo("green");
    }
    
    @Test
    public void usesForSetOfLocalizedEnumJustKey() throws Exception {
        final AttributeDraft draft = AttributeDraft.of("foo", asSet(green, red));
        final Set<String> expected = asSet("green", "red");
        final Set<String> actual = objectMapper.readerFor(new TypeReference<Set<String>>() {
        }).readValue(draft.getValue());
        assertThat(actual).isEqualTo(expected);
    }
    
    @Test
    public void usesForPlainEnumJustKey() throws Exception {
        final AttributeDraft draft = AttributeDraft.of("foo", m);
        assertThat(draft.getValue().asText()).isEqualTo("M");
    }

    @Test
    public void usesForSetOfPlainEnumJustKey() throws Exception {
        final AttributeDraft draft = AttributeDraft.of("foo", asSet(s, m));
        final Set<String> expected = asSet("S", "M");
        final Set<String> actual = objectMapper.readerFor(new TypeReference<Set<String>>() {
        }).readValue(draft.getValue());
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    public void doesNotTouchOtherValueLikeEnumsForLocalizedString() throws Exception {
        final AttributeDraft draft = AttributeDraft.of("foo", LocalizedString.of(ENGLISH, "hello"));
        assertThat(draft.getValue().toString()).isEqualTo("{\"en\":\"hello\"}");
    }

    @Test
    public void doesNotTouchOtherValueLikeEnumsForStrings() throws Exception {
        final AttributeDraft draft = AttributeDraft.of("foo", "baz");
        assertThat(draft.getValue().asText()).isEqualTo("baz");
    }

    @Test
    public void doesNotTouchOtherValueLikeEnumsForReferenceSet() throws Exception {
        final AttributeDraft draft = AttributeDraft.of("foo", asSet(Reference.of("type-id", "id")));
        assertThat(draft.getValue().toString()).isEqualTo("[{\"typeId\":\"type-id\",\"id\":\"id\"}]");
    }

    @Test
    public void shouldSerializeLocalizedEnumValueCorrectly() {
        final LocalizedString localizedString = LocalizedString.of(Locale.GERMAN, "german-text");
        final LocalizedEnumValue localizedEnumValue = LocalizedEnumValue.of("key", localizedString);
        final AttributeDraft attributeDraft = AttributeDraft.of(AttributeAccess.ofLocalizedEnumValue().ofName("name"), localizedEnumValue);
        final JsonNode jsonValue = attributeDraft.getValue();
        assertThat(jsonValue.get("key").asText()).isEqualTo(localizedEnumValue.getKey());
        final JsonNode jsonLabel = jsonValue.get("label");
        assertThat(jsonLabel.get("de").asText()).isEqualTo(localizedEnumValue.getLabel().get(Locale.GERMAN));
    }
}