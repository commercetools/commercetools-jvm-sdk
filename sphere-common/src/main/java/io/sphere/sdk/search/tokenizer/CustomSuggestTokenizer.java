package io.sphere.sdk.search.tokenizer;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.sphere.sdk.models.Base;

import java.util.Collections;
import java.util.List;

public final class CustomSuggestTokenizer extends Base implements SuggestTokenizer {
    private final List<String> inputs;

    @JsonCreator
    private CustomSuggestTokenizer(final List<String> inputs) {
        this.inputs = inputs;
    }

    public List<String> getInputs() {
        return inputs;
    }

    @JsonIgnore
    public static CustomSuggestTokenizer of(final String input) {
        return of(Collections.singletonList(input));
    }

    @JsonIgnore
    public static CustomSuggestTokenizer of(final List<String> inputs) {
        return new CustomSuggestTokenizer(inputs);
    }
}
