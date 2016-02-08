package io.sphere.sdk.products;

import io.sphere.sdk.json.SphereJsonUtils;
import org.junit.Test;

import java.util.Locale;

import static org.assertj.core.api.Assertions.*;

public class SuggestionResultTest {

    @Test
    public void JsonDeserialize() {
        final String jsonString = "{ \"searchKeywords.en\": [{\"text\": \"Multi tool\"}] }";
        final SuggestionResult suggestionResult = SphereJsonUtils.readObject(jsonString, SuggestionResultImpl.typeReference());
        assertThat(suggestionResult.getSuggestionsForLocale(Locale.ENGLISH).get(0).getText()).isEqualTo("Multi tool");
    }
}