package io.sphere.sdk.search;

import com.fasterxml.jackson.annotation.JsonCreator;
import io.sphere.sdk.models.Base;
import io.sphere.sdk.search.tokenizer.SuggestTokenizer;

import javax.annotation.Nullable;

final class SearchKeywordImpl extends Base implements SearchKeyword {
    private final String text;
    @Nullable
    private final SuggestTokenizer suggestTokenizer;

    @JsonCreator
    SearchKeywordImpl(final String text, @Nullable final SuggestTokenizer suggestTokenizer) {
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
}
