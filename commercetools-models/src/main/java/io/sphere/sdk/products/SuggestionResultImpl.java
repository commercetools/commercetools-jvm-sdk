package io.sphere.sdk.products;

import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.core.type.TypeReference;
import io.sphere.sdk.models.Base;

import javax.annotation.Nonnull;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import static java.util.Collections.emptyList;

final class SuggestionResultImpl extends Base implements SuggestionResult {
    private final Map<String, List<Suggestion>> suggestionMap;

    @JsonCreator
    private SuggestionResultImpl(final Map<String, List<Suggestion>> suggestionMap) {
        this.suggestionMap = suggestionMap;
    }

    @Override
    @Nonnull
    public List<Suggestion> getSuggestionsForLocale(final Locale locale) {
        return suggestionMap.getOrDefault("searchKeywords." + locale.toLanguageTag(), emptyList());
    }

    @Override
    public Map<Locale, List<Suggestion>> getSuggestionsMap() {
        final Map<Locale, List<Suggestion>> suggestions = new HashMap<>();
        suggestionMap.entrySet().forEach(entry -> {
            final Locale locale = Locale.forLanguageTag(entry.getKey().replace("searchKeywords.", ""));
            suggestions.put(locale, entry.getValue());
        });
        return suggestions;
    }

    @SuppressWarnings("unused")//used by Jackson JSON mapper
    @JsonAnySetter
    private void set(final String searchKeywordWithLang, final List<Suggestion> suggestions) {
        suggestionMap.put(searchKeywordWithLang, suggestions);
    }

    @JsonIgnore
    public static SuggestionResult of(final Map<Locale, List<Suggestion>> suggestions) {
        final Map<String, List<Suggestion>> suggestionMap = new HashMap<>();
        suggestions.entrySet().forEach(entry -> suggestionMap.put("searchKeywords."+ entry.getKey().toLanguageTag(), entry.getValue()));
        return new SuggestionResultImpl(suggestionMap);
    }

    /**
     * Creates a container which contains the full Java type information to deserialize this class from JSON.
     *
     * @see io.sphere.sdk.json.SphereJsonUtils#readObject(byte[], TypeReference)
     * @see io.sphere.sdk.json.SphereJsonUtils#readObject(String, TypeReference)
     * @see io.sphere.sdk.json.SphereJsonUtils#readObject(com.fasterxml.jackson.databind.JsonNode, TypeReference)
     * @see io.sphere.sdk.json.SphereJsonUtils#readObjectFromResource(String, TypeReference)
     *
     * @return type reference
     */
    public static TypeReference<SuggestionResult> typeReference() {
        return new TypeReference<SuggestionResult>() {
            @Override
            public String toString() {
                return "TypeReference<SuggestionResult>";
            }
        };
    }
}
