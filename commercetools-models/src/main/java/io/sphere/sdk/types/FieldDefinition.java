package io.sphere.sdk.types;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.sphere.sdk.models.LocalizedString;
import io.sphere.sdk.models.TextInputHint;

import javax.annotation.Nullable;

/**
 * @see Custom
 */
@JsonDeserialize(as = FieldDefinitionImpl.class)
public interface FieldDefinition {
    static FieldDefinition of(final FieldType type, final String name, final LocalizedString label, final Boolean required) {
        return of(type, name, label, required, null);
    }

    static FieldDefinition of(final FieldType type, final String name, final LocalizedString label, final Boolean required, final TextInputHint inputHint) {
        return new FieldDefinitionImpl(type, name, label, required, inputHint);
    }

    FieldType getType();

    String getName();

    LocalizedString getLabel();

    @JsonProperty("required")
    Boolean isRequired();

    @Nullable
    TextInputHint getInputHint();
}
