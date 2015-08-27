package io.sphere.sdk.products;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.sphere.sdk.models.Base;

final class SuggestionImpl extends Base implements Suggestion {
    private final String text;

    @JsonCreator
    private SuggestionImpl(final String text) {
        this.text = text;
    }

    @JsonIgnore
    public static Suggestion of(final String text) {
        return new SuggestionImpl(text);
    }

    @Override
    public String getText() {
        return text;
    }
}
