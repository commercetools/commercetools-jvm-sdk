package io.sphere.sdk.orderedits.commands.stagedactions;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.JsonNode;

import javax.annotation.Nullable;

public abstract class OrderEditSetCustomFieldBase extends OrderEditStagedUpdateActionBase {
    protected final String name;
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @Nullable
    protected final JsonNode value;

    protected OrderEditSetCustomFieldBase(String action, final String name, @Nullable JsonNode value) {
        super(action);
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
