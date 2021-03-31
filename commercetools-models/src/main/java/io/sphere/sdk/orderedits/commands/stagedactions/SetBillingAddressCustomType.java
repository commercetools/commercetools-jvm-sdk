package io.sphere.sdk.orderedits.commands.stagedactions;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.databind.JsonNode;
import io.sphere.sdk.models.ResourceIdentifier;
import io.sphere.sdk.types.Type;

import javax.annotation.Nullable;
import java.util.Map;

public final class SetBillingAddressCustomType extends OrderEditSetCustomTypeBase {

    @JsonCreator
    private SetBillingAddressCustomType(@Nullable ResourceIdentifier<Type> type, @Nullable final Map<String, JsonNode> fields) {
        super("setBillingAddressCustomType", type, fields);
    }

    public static SetBillingAddressCustomType of(@Nullable ResourceIdentifier<Type> type, @Nullable Map<String, JsonNode> fields) {
        return new SetBillingAddressCustomType(type, fields);
    }
}
