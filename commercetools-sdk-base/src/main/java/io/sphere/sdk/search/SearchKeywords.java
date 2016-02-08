package io.sphere.sdk.search;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.sphere.sdk.models.Base;

import java.util.*;

import static io.sphere.sdk.utils.SphereInternalUtils.*;

public final class SearchKeywords extends Base {
    @JsonIgnore
    private final Map<Locale, List<SearchKeyword>> content;

    @JsonCreator
    private SearchKeywords() {
        this(new HashMap<>());
    }

    @JsonIgnore
    private SearchKeywords(final Map<Locale, List<SearchKeyword>> content) {
        this.content = content;
    }

    @JsonAnyGetter//@JsonUnwrap supports not maps, but this construct puts map content on top level
    public Map<Locale, List<SearchKeyword>> getContent() {
        return immutableCopyOf(content);
    }

    @JsonAnySetter
    private void setContent(final String languageTag, final List<SearchKeyword> value) {
        content.put(Locale.forLanguageTag(languageTag), value);
    }

    @JsonIgnore
    public static SearchKeywords of(final Locale locale, final List<SearchKeyword> keywords) {
        return of(mapOf(locale, keywords));
    }

    @JsonIgnore
    public static SearchKeywords of(final Locale locale1, final List<SearchKeyword> keywords1, final Locale locale2, final List<SearchKeyword> keywords2) {
        final Map<Locale, List<SearchKeyword>> map = new HashMap<>();
        map.put(locale1, keywords1);
        map.put(locale2, keywords2);
        return of(map);
    }

    @JsonIgnore
    public static SearchKeywords of(final Map<Locale, List<SearchKeyword>> content) {
        return new SearchKeywords(content);
    }

    @JsonIgnore
    public static SearchKeywords of() {
        return of(Collections.emptyMap());
    }
}
