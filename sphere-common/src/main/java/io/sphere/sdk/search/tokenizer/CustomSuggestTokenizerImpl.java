package io.sphere.sdk.search.tokenizer;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.sphere.sdk.models.Base;

import java.util.Collections;
import java.util.List;

final class CustomSuggestTokenizerImpl extends Base implements CustomSuggestTokenizer {
    private final List<String> inputs;

    @JsonCreator
    CustomSuggestTokenizerImpl(final List<String> inputs) {
        this.inputs = inputs;
    }

    @Override
    public List<String> getInputs() {
        return inputs;
    }
}
