package io.sphere.sdk.orderedits.commands.stagedactions;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.JsonNode;
import io.sphere.sdk.json.SphereJsonUtils;

import javax.annotation.Nullable;

public final class SetLineItemCustomField extends OrderEditStagedUpdateActionBase {

    private final String lineItemId;

    private final String name;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @Nullable
    private final JsonNode value;

    @JsonCreator
    private SetLineItemCustomField(final String name, @Nullable final JsonNode value, final String lineItemId) {
        super("setLineItemCustomField");
        this.name = name;
        this.value = value;
        this.lineItemId = lineItemId;
    }

    public static SetLineItemCustomField of(final String name, @Nullable final JsonNode value, final String lineItemId) {
        return new SetLineItemCustomField(name, value, lineItemId);
    }

    public static SetLineItemCustomField ofJson(final String name, final JsonNode value, final String lineItemId) {
        return new SetLineItemCustomField(name, value, lineItemId);
    }

    public static SetLineItemCustomField ofObject(final String name, final Object value, final String lineItemId) {
        final JsonNode jsonNode = SphereJsonUtils.toJsonNode(value);
        return ofJson(name, jsonNode, lineItemId);
    }

    public static SetLineItemCustomField ofUnset(final String name, final String lineItemId) {
        return ofJson(name, null, lineItemId);
    }

    public String getLineItemId() {
        return lineItemId;
    }

    public String getName() {
        return name;
    }

    @Nullable
    public JsonNode getValue() {
        return value;
    }
}