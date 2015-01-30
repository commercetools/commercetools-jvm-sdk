package io.sphere.sdk.search.tokenizer;

import io.sphere.sdk.models.Base;

public class WhiteSpaceSuggestTokenizer extends Base implements SuggestTokenizer {
    private WhiteSpaceSuggestTokenizer() {
    }

    public static WhiteSpaceSuggestTokenizer of() {
        return new WhiteSpaceSuggestTokenizer();
    }
}
