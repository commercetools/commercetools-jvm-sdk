package io.sphere.sdk.orderedits.commands.updateactions;

import com.fasterxml.jackson.databind.JsonNode;
import io.sphere.sdk.commands.UpdateActionImpl;
import io.sphere.sdk.models.ResourceIdentifier;
import io.sphere.sdk.orderedits.OrderEdit;
import io.sphere.sdk.types.Type;

import javax.annotation.Nullable;
import java.util.Map;

public final class SetCustomType extends UpdateActionImpl<OrderEdit> {

    @Nullable
    private final ResourceIdentifier<Type> type;

    @Nullable
    private final Map<String, JsonNode> fields;

    private SetCustomType(@Nullable final ResourceIdentifier<Type> type, @Nullable final Map<String, JsonNode> fields) {
        super("setCustomType");
        this.type = type;
        this.fields = fields;
    }

    public static SetCustomType of(@Nullable final ResourceIdentifier<Type> type, @Nullable final Map<String, JsonNode> fields) {
        return new SetCustomType(type, fields);
    }

    @Nullable
    public ResourceIdentifier<Type> getType() {
        return type;
    }

    @Nullable
    public Map<String, JsonNode> getFields() {
        return fields;
    }
}
