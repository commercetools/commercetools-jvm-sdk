package io.sphere.sdk.search;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.sphere.sdk.models.Base;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import static io.sphere.sdk.utils.SphereInternalUtils.immutableCopyOf;

final class SearchKeywordsImpl extends Base implements SearchKeywords {
    @JsonIgnore
    private final Map<Locale, List<SearchKeyword>> content;

    @JsonCreator
    SearchKeywordsImpl() {
        this(new HashMap<>());
    }

    @JsonIgnore
    SearchKeywordsImpl(final Map<Locale, List<SearchKeyword>> content) {
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
}
