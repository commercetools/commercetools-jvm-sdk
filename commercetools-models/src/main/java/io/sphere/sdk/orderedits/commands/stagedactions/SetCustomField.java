package io.sphere.sdk.orderedits.commands.stagedactions;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.JsonNode;
import io.sphere.sdk.json.SphereJsonUtils;

import javax.annotation.Nullable;

public final class SetCustomField extends OrderEditStagedUpdateActionBase {

    private final String name;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @Nullable
    private final JsonNode value;

    private SetCustomField(final String name, @Nullable final JsonNode value) {
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

    public static SetCustomField ofJson(final String name, final JsonNode value) {
        return new SetCustomField(name, value);
    }

    public static SetCustomField ofObject(final String name, final Object value) {
        final JsonNode jsonNode = SphereJsonUtils.toJsonNode(value);
        return ofJson(name, jsonNode);
    }

    public static SetCustomField ofUnset(final String name) {
        return ofJson(name, null);
    }
}