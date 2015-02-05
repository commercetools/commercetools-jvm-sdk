package io.sphere.sdk.search.tokenizer;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.sphere.sdk.models.Base;

import java.util.List;

import static java.util.Arrays.asList;

public class CustomSuggestTokenizer extends Base implements SuggestTokenizer {
    private final List<String> inputs;

    private CustomSuggestTokenizer(final List<String> inputs) {
        this.inputs = inputs;
    }

    public List<String> getInputs() {
        return inputs;
    }

    @JsonIgnore
    public static CustomSuggestTokenizer of(final String input) {
        return of(asList(input));
    }

    @JsonIgnore
    public static CustomSuggestTokenizer of(final List<String> inputs) {
        return new CustomSuggestTokenizer(inputs);
    }
}
