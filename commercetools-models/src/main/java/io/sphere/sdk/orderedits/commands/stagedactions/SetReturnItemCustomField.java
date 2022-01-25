package io.sphere.sdk.orderedits.commands.stagedactions;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.JsonNode;
import io.sphere.sdk.json.SphereJsonUtils;

import javax.annotation.Nullable;

public final class SetReturnItemCustomField extends OrderEditStagedUpdateActionBase {

    private final String returnItemId;

    private final String name;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @Nullable
    private final JsonNode value;

    @JsonCreator
    private SetReturnItemCustomField(final String name, @Nullable final JsonNode value, final String returnItemId) {
        super("setReturnItemCustomField");
        this.name = name;
        this.value = value;
        this.returnItemId = returnItemId;
    }

    public static SetReturnItemCustomField of(final String name, @Nullable final JsonNode value, final String returnItemId) {
        return new SetReturnItemCustomField(name, value, returnItemId);
    }

    public static SetReturnItemCustomField ofJson(final String name, final JsonNode value, final String returnItemId) {
        return new SetReturnItemCustomField(name, value, returnItemId);
    }

    public static SetReturnItemCustomField ofObject(final String name, final Object value, final String returnItemId) {
        final JsonNode jsonNode = SphereJsonUtils.toJsonNode(value);
        return ofJson(name, jsonNode, returnItemId);
    }

    public static SetReturnItemCustomField ofUnset(final String name, final String returnItemId) {
        return ofJson(name, null, returnItemId);
    }

    public String getReturnItemId() {
        return returnItemId;
    }

    public String getName() {
        return name;
    }

    @Nullable
    public JsonNode getValue() {
        return value;
    }
}