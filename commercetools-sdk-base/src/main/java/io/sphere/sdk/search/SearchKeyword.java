package io.sphere.sdk.search;

import com.fasterxml.jackson.annotation.JsonCreator;
import io.sphere.sdk.models.Base;
import io.sphere.sdk.search.tokenizer.SuggestTokenizer;

import javax.annotation.Nullable;

public final class SearchKeyword extends Base {
    private final String text;
    @Nullable
    private final SuggestTokenizer suggestTokenizer;

    @JsonCreator
    private SearchKeyword(final String text, @Nullable final SuggestTokenizer suggestTokenizer) {
        this.suggestTokenizer = suggestTokenizer;
        this.text = text;
    }

    @Nullable
    public SuggestTokenizer getSuggestTokenizer() {
        return suggestTokenizer;
    }

    public String getText() {
        return text;
    }

    public static SearchKeyword of(final String text) {
        return of(text, null);
    }

    public static SearchKeyword of(final String text, final SuggestTokenizer suggestTokenizer) {
        return new SearchKeyword(text, suggestTokenizer);
    }
}
