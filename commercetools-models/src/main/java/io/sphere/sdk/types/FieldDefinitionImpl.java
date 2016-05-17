package io.sphere.sdk.types;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.sphere.sdk.models.Base;
import io.sphere.sdk.models.LocalizedString;
import io.sphere.sdk.models.TextInputHint;

import javax.annotation.Nullable;

final class FieldDefinitionImpl extends Base implements FieldDefinition {
    private final FieldType type;
    private final String name;
    private final LocalizedString label;
    @JsonProperty("required")
    private final Boolean required;
    @Nullable
    private final TextInputHint inputHint;

    @JsonCreator
    FieldDefinitionImpl(final FieldType type, final String name, final LocalizedString label, final Boolean required, @Nullable final TextInputHint inputHint) {
        this.type = type;
        this.name = name;
        this.label = label;
        this.required = required;
        this.inputHint = inputHint;
    }

    public FieldType getType() {
        return type;
    }

    public String getName() {
        return name;
    }

    public LocalizedString getLabel() {
        return label;
    }

    @JsonProperty("required")
    public Boolean isRequired() {
        return required;
    }

    @Nullable
    public TextInputHint getInputHint() {
        return inputHint;
    }
}
