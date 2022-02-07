package io.sphere.sdk.orderedits.commands.stagedactions;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.JsonNode;
import io.sphere.sdk.json.SphereJsonUtils;

import javax.annotation.Nullable;

public final class SetParcelCustomField extends OrderEditStagedUpdateActionBase {

    private final String parcelId;

    private final String name;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @Nullable
    private final JsonNode value;

    @JsonCreator
    private SetParcelCustomField(final String name, @Nullable final JsonNode value, final String parcelId) {
        super("setParcelCustomField");
        this.name = name;
        this.value = value;
        this.parcelId = parcelId;
    }

    public static SetParcelCustomField of(final String name, @Nullable final JsonNode value, final String parcelId) {
        return new SetParcelCustomField(name, value, parcelId);
    }

    public static SetParcelCustomField ofJson(final String name, final JsonNode value, final String parcelId) {
        return new SetParcelCustomField(name, value, parcelId);
    }

    public static SetParcelCustomField ofObject(final String name, final Object value, final String parcelId) {
        final JsonNode jsonNode = SphereJsonUtils.toJsonNode(value);
        return ofJson(name, jsonNode, parcelId);
    }

    public static SetParcelCustomField ofUnset(final String name, final String parcelId) {
        return ofJson(name, null, parcelId);
    }

    public String getParcelId() {
        return parcelId;
    }

    public String getName() {
        return name;
    }

    @Nullable
    public JsonNode getValue() {
        return value;
    }
}