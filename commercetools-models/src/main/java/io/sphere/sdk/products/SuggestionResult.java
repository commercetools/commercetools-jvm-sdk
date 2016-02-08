package io.sphere.sdk.products;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.Locale;
import java.util.Map;

@JsonDeserialize(as = SuggestionResultImpl.class)
public interface SuggestionResult {
    @Nonnull
    List<Suggestion> getSuggestionsForLocale(Locale locale);

    Map<Locale, List<Suggestion>> getSuggestionsMap();

    @JsonIgnore
    static SuggestionResult of(final Map<Locale, List<Suggestion>> suggestions) {
        return SuggestionResultImpl.of(suggestions);
    }

}
