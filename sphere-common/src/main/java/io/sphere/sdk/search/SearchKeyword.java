package io.sphere.sdk.search;

import com.fasterxml.jackson.annotation.JsonCreator;
import io.sphere.sdk.models.Base;
import io.sphere.sdk.search.tokenizer.SuggestTokenizer;

import java.util.Optional;

public class SearchKeyword extends Base {
    private final String text;
    private final Optional<SuggestTokenizer> suggestTokenizer;

    @JsonCreator
    private SearchKeyword(final String text, final Optional<SuggestTokenizer> suggestTokenizer) {
        this.suggestTokenizer = suggestTokenizer;
        this.text = text;
    }

    public Optional<SuggestTokenizer> getSuggestTokenizer() {
        return suggestTokenizer;
    }

    public String getText() {
        return text;
    }

    public static SearchKeyword of(final String text) {
        return of(text, Optional.empty());
    }

    public static SearchKeyword of(final String text, final SuggestTokenizer suggestTokenizer) {
        return of(text, Optional.of(suggestTokenizer));
    }

    public static SearchKeyword of(final String text, final Optional<SuggestTokenizer> suggestTokenizer) {
        return new SearchKeyword(text, suggestTokenizer);
    }
}
