package io.sphere.sdk.categories.commands.updateactions;

import com.fasterxml.jackson.databind.JsonNode;
import io.sphere.sdk.categories.Category;
import io.sphere.sdk.models.ResourceIdentifier;
import io.sphere.sdk.types.CustomFieldsDraft;
import io.sphere.sdk.types.customupdateactions.SetCustomTypeBase;

import javax.annotation.Nullable;
import java.util.Map;
import java.util.Optional;

/**
 * This action sets, overwrites or removes the custom type and fields for an existing Asset.
 *
 * @see io.sphere.sdk.types.Custom
 */
public final class SetAssetCustomType extends SetCustomTypeBase<Category> {
    private final String assetId;
    private final String assetKey;

    private SetAssetCustomType(final String assetId, final String assetKey, @Nullable final String typeId, @Nullable final String typeKey, @Nullable final Map<String, JsonNode> fields) {
        super("setAssetCustomType", typeId, typeKey, fields);
        this.assetId = assetId;
        this.assetKey = assetKey;
    }

    public String getAssetId() {
        return assetId;
    }

    public String getAssetKey() {
        return assetKey;
    }

    public static SetAssetCustomType of(final String assetId, @Nullable final CustomFieldsDraft customFieldsDraft) {
        return of(assetId,null, customFieldsDraft);
    }

    public static SetAssetCustomType ofKey(final String assetKey, @Nullable final CustomFieldsDraft customFieldsDraft) {
        return of(null,assetKey, customFieldsDraft);
    }

    private static SetAssetCustomType of(final String assetId,final String assetKey, @Nullable final CustomFieldsDraft customFieldsDraft) {
        final Optional<CustomFieldsDraft> draft = Optional.ofNullable(customFieldsDraft);
        final String typeId = draft.map(CustomFieldsDraft::getType).map(ResourceIdentifier::getId).orElse(null);
        final String typeKey = draft.map(CustomFieldsDraft::getType).map(ResourceIdentifier::getKey).orElse(null);
        final Map<String, JsonNode> fields = draft.map(CustomFieldsDraft::getFields).orElse(null);
        return new SetAssetCustomType(assetId,assetKey, typeId, typeKey, fields);
    }
}
