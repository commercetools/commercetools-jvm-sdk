package io.sphere.sdk.search;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.sphere.sdk.search.tokenizer.SuggestTokenizer;

import javax.annotation.Nullable;

@JsonDeserialize(as = SearchKeywordImpl.class)
public interface SearchKeyword {
    @Nullable
    SuggestTokenizer getSuggestTokenizer();

    String getText();

    static SearchKeyword of(final String text) {
        return of(text, null);
    }

    static SearchKeyword of(final String text, final SuggestTokenizer suggestTokenizer) {
        return new SearchKeywordImpl(text, suggestTokenizer);
    }
}
