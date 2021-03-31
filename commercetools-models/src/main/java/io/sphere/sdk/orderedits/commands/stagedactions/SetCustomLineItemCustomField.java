package io.sphere.sdk.orderedits.commands.stagedactions;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.JsonNode;

import javax.annotation.Nullable;

public final class SetCustomLineItemCustomField extends OrderEditSetCustomFieldBase {

    private final String customLineItemId;

    @JsonCreator
    private SetCustomLineItemCustomField(final String customLineItemId, final String name, @Nullable JsonNode value) {
        super("setCustomLineItemCustomField", name, value);
        this.customLineItemId = customLineItemId;
    }

    public static SetCustomLineItemCustomField of(final String customLineItemId, final String name, @Nullable JsonNode value) {
        return new SetCustomLineItemCustomField(customLineItemId, name, value);
    }

    public String getCustomLineItemId() {
        return customLineItemId;
    }
}
