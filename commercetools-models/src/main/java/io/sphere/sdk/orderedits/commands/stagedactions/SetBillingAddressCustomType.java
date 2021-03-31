package io.sphere.sdk.orderedits.commands.stagedactions;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.JsonNode;
import io.sphere.sdk.carts.Cart;
import io.sphere.sdk.json.SphereJsonUtils;
import io.sphere.sdk.models.ResourceIdentifier;
import io.sphere.sdk.types.Type;
import io.sphere.sdk.types.customupdateactions.SetCustomTypeBase;

import javax.annotation.Nullable;
import java.util.Collections;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Sets or removes a custom type in a custom line item.
 *
 * {@doc.gen intro}
 *
 * @see io.sphere.sdk.types.Custom
 */
public final class SetBillingAddressCustomType extends OrderEditSetCustomTypeBase {

    @JsonCreator
    private SetBillingAddressCustomType(@Nullable ResourceIdentifier<Type> type, @Nullable final Map<String, JsonNode> fields) {
        super("setBillingAddressCustomType", type, fields);
    }

    public static SetBillingAddressCustomType of(@Nullable ResourceIdentifier<Type> type, @Nullable Map<String, JsonNode> fields) {
        return new SetBillingAddressCustomType(type, fields);
    }
}
