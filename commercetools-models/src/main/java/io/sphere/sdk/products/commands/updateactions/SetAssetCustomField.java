package io.sphere.sdk.products.commands.updateactions;

import com.fasterxml.jackson.databind.JsonNode;
import io.sphere.sdk.json.SphereJsonUtils;
import io.sphere.sdk.products.Product;
import io.sphere.sdk.types.customupdateactions.SetCustomFieldBase;

import javax.annotation.Nullable;

/**
 * This action sets, overwrites or removes any existing custom field for an existing Asset.
 *
 * {@doc.gen intro}
 *
 * <p>By variant ID (every variant has a variantId):</p>
 * {@include.example io.sphere.sdk.products.commands.ProductUpdateCommandIntegrationTest#assetCustomTypeByVariantId()}
 *
 * <p>By SKU (attention, SKU is optional field in a variant):</p>
 * {@include.example io.sphere.sdk.products.commands.ProductUpdateCommandIntegrationTest#assetCustomTypeBySku()}
 *
 * @see io.sphere.sdk.types.Custom
 */
public final class SetAssetCustomField extends SetCustomFieldBase<Product> {
    @Nullable
    private final Integer variantId;
    @Nullable
    private final String sku;
    private final String assetId;

    private SetAssetCustomField(final String assetId, @Nullable final Integer variantId, @Nullable final String sku, final String name, final JsonNode value) {
        super("setAssetCustomField", name, value);
        this.assetId = assetId;
        this.variantId = variantId;
        this.sku = sku;
    }

    public String getAssetId() {
        return assetId;
    }

    @Nullable
    public Integer getVariantId() {
        return variantId;
    }

    @Nullable
    public String getSku() {
        return sku;
    }

    public static SetAssetCustomField ofVariantIdUsingJson(final Integer variantId, final String assetId, final String name, final JsonNode value) {
        return new SetAssetCustomField(assetId, variantId, null, name, value);
    }

    public static SetAssetCustomField ofSkuUsingJson(final String sku, final String assetId, final String name, final JsonNode value) {
        return new SetAssetCustomField(assetId, null, sku, name, value);
    }

    public static SetAssetCustomField ofVariantId(final Integer variantId, final String assetId, final String name, final Object value) {
        return ofVariantIdUsingJson(variantId, assetId, name, SphereJsonUtils.toJsonNode(value));
    }

    public static SetAssetCustomField ofSku(final String sku, final String assetId, final String name, final Object value) {
        return ofSkuUsingJson(sku, assetId, name, SphereJsonUtils.toJsonNode(value));
    }
}
