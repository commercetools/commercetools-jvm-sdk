package io.sphere.sdk.products;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

@JsonDeserialize(as = SuggestionImpl.class)
public interface Suggestion {
    String getText();

    @JsonIgnore
    static Suggestion of(final String text) {
        return SuggestionImpl.of(text);
    }
}
