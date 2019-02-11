package io.sphere.sdk.orderedits.commands.stagedactions;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.JsonNode;

import javax.annotation.Nullable;

public final class SetCustomLineItemCustomField extends OrderEditStagedUpdateActionBase {

    private final String customLineItemId;

    private final String name;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @Nullable
    private final JsonNode value;

    @JsonCreator
    private SetCustomLineItemCustomField(final String customLineItemId, final String name, @Nullable JsonNode value) {
        super("setCustomLineItemCustomField");
        this.customLineItemId = customLineItemId;
        this.name = name;
        this.value = value;
    }

    public static SetCustomLineItemCustomField of(final String customLineItemId, final String name, @Nullable JsonNode value) {
        return new SetCustomLineItemCustomField(customLineItemId, name, value);
    }

    public String getName() {
        return name;
    }

    @Nullable
    public JsonNode getValue() {
        return value;
    }

    public String getCustomLineItemId() {
        return customLineItemId;
    }
}
