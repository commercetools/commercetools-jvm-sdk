package io.sphere.sdk.types.customupdateactions;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.JsonNode;
import io.sphere.sdk.commands.UpdateActionImpl;

import javax.annotation.Nullable;

public abstract class SetCustomFieldBase<T> extends UpdateActionImpl<T> {
    private final String name;
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @Nullable
    private final JsonNode value;

    protected SetCustomFieldBase(final String name, final JsonNode value) {
        super("setCustomField");
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    @Nullable
    public JsonNode getValue() {
        return value;
    }
}
