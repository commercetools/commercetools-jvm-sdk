package io.sphere.sdk.search.tokenizer;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import java.util.Collections;
import java.util.List;

/**
 * Custom tokenizer allows to define arbitrary tokens which are used to match the input.
 */
@JsonDeserialize(as = CustomSuggestTokenizerImpl.class)
public interface CustomSuggestTokenizer extends SuggestTokenizer {
    List<String> getInputs();


    @JsonIgnore
    static CustomSuggestTokenizer of(final String input) {
        return of(Collections.singletonList(input));
    }

    @JsonIgnore
    static CustomSuggestTokenizer of(final List<String> inputs) {
        return new CustomSuggestTokenizerImpl(inputs);
    }
}
