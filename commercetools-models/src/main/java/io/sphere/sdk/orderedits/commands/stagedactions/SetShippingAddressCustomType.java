package io.sphere.sdk.orderedits.commands.stagedactions;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.databind.JsonNode;
import io.sphere.sdk.models.ResourceIdentifier;
import io.sphere.sdk.types.Type;

import javax.annotation.Nullable;
import java.util.Map;

/**
 * Sets or removes a custom type in a custom line item.
 *
 * {@doc.gen intro}
 *
 * @see io.sphere.sdk.types.Custom
 */
public final class SetShippingAddressCustomType extends OrderEditSetCustomTypeBase {

    @JsonCreator
    private SetShippingAddressCustomType(@Nullable ResourceIdentifier<Type> type, @Nullable final Map<String, JsonNode> fields) {
        super("setShippingAddressCustomType", type, fields);
    }

    public static SetShippingAddressCustomType of(@Nullable ResourceIdentifier<Type> type, @Nullable Map<String, JsonNode> fields) {
        return new SetShippingAddressCustomType(type, fields);
    }
}
