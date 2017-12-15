package io.sphere.sdk.categories.commands.updateactions;

import com.fasterxml.jackson.databind.JsonNode;
import io.sphere.sdk.categories.Category;
import io.sphere.sdk.json.SphereJsonUtils;
import io.sphere.sdk.types.customupdateactions.SetCustomFieldBase;

/**
 * This action sets, overwrites or removes any existing custom field for an existing Asset.
 *
 * @see io.sphere.sdk.types.Custom
 */
public final class SetAssetCustomField extends SetCustomFieldBase<Category> {
    private final String assetId;
    private final String assetKey;

    private SetAssetCustomField(final String assetId, final String assetKey, final String name, final JsonNode value) {
        super("setAssetCustomField", name, value);
        this.assetId = assetId;
        this.assetKey = assetKey;
    }

    public String getAssetId() {
        return assetId;
    }

    public String getAssetKey() {
        return assetKey;
    }

    public static SetAssetCustomField ofJsonValue(final String assetId, final String name, final JsonNode value) {
        return new SetAssetCustomField(assetId, null, name, value);
    }

    public static SetAssetCustomField of(final String assetId, final String name, final Object value) {
        return ofJsonValue(assetId, name, SphereJsonUtils.toJsonNode(value));
    }

    public static SetAssetCustomField ofJsonValueWithKey(final String assetKey, final String name, final JsonNode value) {
        return new SetAssetCustomField(null, assetKey, name, value);
    }

    public static SetAssetCustomField ofKey(final String assetKey, final String name, final Object value) {
        return ofJsonValueWithKey(assetKey, name, SphereJsonUtils.toJsonNode(value));
    }
}