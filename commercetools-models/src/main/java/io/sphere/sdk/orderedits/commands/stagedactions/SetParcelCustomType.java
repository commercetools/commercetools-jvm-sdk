package io.sphere.sdk.orderedits.commands.stagedactions;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.JsonNode;
import io.sphere.sdk.json.SphereJsonUtils;
import io.sphere.sdk.models.ResourceIdentifier;
import io.sphere.sdk.types.Type;

import javax.annotation.Nullable;
import java.util.Map;
import java.util.stream.Collectors;

public final class SetParcelCustomType extends OrderEditStagedUpdateActionBase {

    private final String parcelId;

    @Nullable
    private final ResourceIdentifier<Type> type;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @Nullable
    private final Map<String, JsonNode> fields;

    private SetParcelCustomType(final String parcelId, @Nullable final String typeId, @Nullable final String typeKey, @Nullable final Map<String, JsonNode> fields) {
        super("setParcelCustomType");
        this.parcelId = parcelId;
        this.type = ResourceIdentifier.ofIdOrKey(typeId, typeKey);
        this.fields = fields;
    }

    public static SetParcelCustomType of(final String parcelId, @Nullable final String typeId, @Nullable final String typeKey, @Nullable final Map<String, JsonNode> fields) {
        return new SetParcelCustomType(parcelId, typeId, typeKey, fields);
    }

    public static SetParcelCustomType of(final String parcelId){
        return new SetParcelCustomType(parcelId, null, null, null);
    }

    @Nullable
    public ResourceIdentifier<Type> getType() {
        return type;
    }

    @Nullable
    public Map<String, JsonNode> getFields() {
        return fields;
    }


    protected static Map<String, JsonNode> mapObjectToJsonMap(final Map<String, Object> fields) {
        return fields.entrySet().stream()
                .collect(Collectors.toMap(entry -> entry.getKey(),
                        entry -> SphereJsonUtils.toJsonNode(entry.getValue())));
    }

    public String getParcelId() {
        return parcelId;
    }
}