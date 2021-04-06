package io.sphere.sdk.orderedits.commands.stagedactions;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.JsonNode;
import io.sphere.sdk.json.SphereJsonUtils;
import io.sphere.sdk.models.ResourceIdentifier;
import io.sphere.sdk.types.Type;

import javax.annotation.Nullable;
import java.util.Map;
import java.util.stream.Collectors;

public final class SetCustomLineItemCustomType extends OrderEditSetCustomTypeBase {

    private final String customLineItemId;

    @JsonCreator
    private SetCustomLineItemCustomType(String customLineItemId, @Nullable ResourceIdentifier<Type> type, @Nullable Map<String, JsonNode> fields) {
        super("setCustomLineItemCustomType", type, fields);
        this.customLineItemId = customLineItemId;
    }

    public static SetCustomLineItemCustomType of(String customLineItemId, @Nullable ResourceIdentifier<Type> type, @Nullable Map<String, JsonNode> fields) {
        return new SetCustomLineItemCustomType(customLineItemId, type, fields);
    }

    public String getCustomLineItemId() {
        return customLineItemId;
    }

}
