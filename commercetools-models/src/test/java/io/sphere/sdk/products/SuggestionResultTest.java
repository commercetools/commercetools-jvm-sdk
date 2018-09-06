package io.sphere.sdk.products;

import io.sphere.sdk.json.SphereJsonUtils;
import org.junit.Test;

import java.util.*;

import static org.assertj.core.api.Assertions.*;

public class SuggestionResultTest {

    @Test
    public void JsonDeserialize() {
        final String jsonString = "{ \"searchKeywords.en\": [{\"text\": \"Multi tool\"}] }";
        final SuggestionResult suggestionResult = SphereJsonUtils.readObject(jsonString, SuggestionResultImpl.typeReference());
        assertThat(suggestionResult.getSuggestionsForLocale(Locale.ENGLISH).get(0).getText()).isEqualTo("Multi tool");
    }

    @Test
    public void testSuggestionResult() {

        final Suggestion suggestion = Suggestion.of("Hello world");
        final List<Suggestion> suggestionsList =Arrays.asList(suggestion);
        final Map<Locale, List<Suggestion>> suggestionsMap = new HashMap<>();
        suggestionsMap.put(Locale.GERMANY, suggestionsList);
        final SuggestionResult result = SuggestionResult.of(suggestionsMap);

        assertThat( result.getSuggestionsForLocale(Locale.GERMANY)).hasSize(1);
        assertThat( result.getSuggestionsForLocale(Locale.GERMANY).get(0)).isEqualTo(suggestion);
    }
}