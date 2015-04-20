package io.sphere.sdk.search.tokenizer;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = WhiteSpaceSuggestTokenizer.class, name = "whitespace"),
        @JsonSubTypes.Type(value = CustomSuggestTokenizer.class, name = "custom") })
public interface SuggestTokenizer {
}
