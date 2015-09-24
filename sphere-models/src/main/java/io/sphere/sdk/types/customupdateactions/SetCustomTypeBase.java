package io.sphere.sdk.types.customupdateactions;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.JsonNode;
import io.sphere.sdk.commands.UpdateActionImpl;

import javax.annotation.Nullable;
import java.util.Map;

public abstract class SetCustomTypeBase<T> extends UpdateActionImpl<T> {
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @Nullable
    private final String typeId;
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @Nullable
    private final String typeKey;
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @Nullable
    private final Map<String, JsonNode> fields;

    protected SetCustomTypeBase(@Nullable final String typeId, @Nullable final String typeKey, @Nullable final Map<String, JsonNode> fields) {
        this("setCustomType", typeId, typeKey, fields);
    }

    protected SetCustomTypeBase(final String action, @Nullable final String typeId, @Nullable final String typeKey, @Nullable final Map<String, JsonNode> fields) {
        super(action);
        this.typeId = typeId;
        this.typeKey = typeKey;
        this.fields = fields;
    }

    @Nullable
    public String getTypeId() {
        return typeId;
    }

    @Nullable
    public String getTypeKey() {
        return typeKey;
    }

    @Nullable
    public Map<String, JsonNode> getFields() {
        return fields;
    }
}
