package io.sphere.sdk.search;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import java.util.*;

import static io.sphere.sdk.utils.SphereInternalUtils.mapOf;

@JsonDeserialize(as = SearchKeywordsImpl.class)
public interface SearchKeywords {
    Map<Locale, List<SearchKeyword>> getContent();

    @JsonIgnore
    static SearchKeywords of(final Locale locale, final List<SearchKeyword> keywords) {
        return of(mapOf(locale, keywords));
    }

    @JsonIgnore
    static SearchKeywords of(final Locale locale1, final List<SearchKeyword> keywords1, final Locale locale2, final List<SearchKeyword> keywords2) {
        final Map<Locale, List<SearchKeyword>> map = new HashMap<>();
        map.put(locale1, keywords1);
        map.put(locale2, keywords2);
        return of(map);
    }

    @JsonIgnore
    static SearchKeywords of(final Map<Locale, List<SearchKeyword>> content) {
        return new SearchKeywordsImpl(content);
    }

    @JsonIgnore
    static SearchKeywords of() {
        return of(Collections.emptyMap());
    }
}
