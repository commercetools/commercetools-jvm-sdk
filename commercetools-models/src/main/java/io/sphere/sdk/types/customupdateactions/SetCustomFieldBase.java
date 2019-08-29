package io.sphere.sdk.types.customupdateactions;

import com.fasterxml.jackson.databind.JsonNode;
import io.sphere.sdk.commands.UpdateActionImpl;

import javax.annotation.Nullable;

public abstract class SetCustomFieldBase<T> extends UpdateActionImpl<T> {
    private final String name;
    @Nullable
    private final JsonNode value;

    protected SetCustomFieldBase(final String action, final String name, @Nullable final JsonNode value) {
        super(action);
        this.name = name;
        this.value = value;
    }

    protected SetCustomFieldBase(final String name, final JsonNode value) {
        this("setCustomField", name, value);
    }

    public String getName() {
        return name;
    }

    @Nullable
    public JsonNode getValue() {
        return value;
    }
}
