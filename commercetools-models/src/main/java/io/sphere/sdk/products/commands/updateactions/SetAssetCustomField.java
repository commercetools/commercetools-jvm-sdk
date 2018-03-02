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
    private final String assetKey;
    @Nullable
    private final Boolean staged;

    private SetAssetCustomField(final String assetId,final String assetKey, @Nullable final Integer variantId, @Nullable final String sku, final String name, final JsonNode value, @Nullable final Boolean staged) {
        super("setAssetCustomField", name, value);
        this.assetId = assetId;
        this.assetKey = assetKey;
        this.variantId = variantId;
        this.sku = sku;
        this.staged = staged;
    }

    public String getAssetId() {
        return assetId;
    }

    public String getAssetKey() {
        return assetKey;
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
        return ofVariantIdUsingJson(variantId, assetId, name, value, null);
    }

    public static SetAssetCustomField ofVariantIdUsingJson(final Integer variantId, final String assetId, final String name, final JsonNode value, @Nullable final Boolean staged) {
        return new SetAssetCustomField(assetId,null, variantId, null, name, value, staged);
    }

    public static SetAssetCustomField ofSkuUsingJson(final String sku, final String assetId, final String name, final JsonNode value) {
        return ofSkuUsingJson(sku, assetId, name, value, null);
    }

    public static SetAssetCustomField ofSkuUsingJson(final String sku, final String assetId, final String name, final JsonNode value, @Nullable final Boolean staged) {
        return new SetAssetCustomField(assetId,null, null, sku, name, value, staged);
    }

    public static SetAssetCustomField ofVariantId(final Integer variantId, final String assetId, final String name, final Object value) {
        return ofVariantId(variantId, assetId, name, value, null);
    }

    public static SetAssetCustomField ofVariantId(final Integer variantId, final String assetId, final String name, final Object value, @Nullable final Boolean staged) {
        return ofVariantIdUsingJson(variantId, assetId, name, SphereJsonUtils.toJsonNode(value), staged);
    }

    public static SetAssetCustomField ofSku(final String sku, final String assetId, final String name, final Object value) {
        return ofSku(sku, assetId, name, value, null);
    }

    public static SetAssetCustomField ofSku(final String sku, final String assetId, final String name, final Object value, @Nullable final Boolean staged) {
        return ofSkuUsingJson(sku, assetId, name, SphereJsonUtils.toJsonNode(value), staged);
    }
    
    
    public static SetAssetCustomField ofVariantIdUsingJsonAndAssetKey(final Integer variantId, final String assetKey, final String name, final JsonNode value) {
        return ofVariantIdUsingJsonAndAssetKey(variantId, assetKey, name, value, null);
    }

    public static SetAssetCustomField ofVariantIdUsingJsonAndAssetKey(final Integer variantId, final String assetKey, final String name, final JsonNode value, @Nullable final Boolean staged) {
        return new SetAssetCustomField(null, assetKey, variantId, null, name, value, staged);
    }

    public static SetAssetCustomField ofSkuUsingJsonAndAssetKey(final String sku, final String assetKey, final String name, final JsonNode value) {
        return ofSkuUsingJsonAndAssetKey(sku, assetKey, name, value, null);
    }

    public static SetAssetCustomField ofSkuUsingJsonAndAssetKey(final String sku, final String assetKey, final String name, final JsonNode value, @Nullable final Boolean staged) {
        return new SetAssetCustomField(null,assetKey, null, sku, name, value, staged);
    }

    public static SetAssetCustomField ofVariantIdAndAssetKey(final Integer variantId, final String assetKey, final String name, final Object value) {
        return ofVariantIdAndAssetKey(variantId, assetKey, name, value, null);
    }

    public static SetAssetCustomField ofVariantIdAndAssetKey(final Integer variantId, final String assetKey, final String name, final Object value, @Nullable final Boolean staged) {
        return ofVariantIdUsingJsonAndAssetKey(variantId, assetKey, name, SphereJsonUtils.toJsonNode(value), staged);
    }

    public static SetAssetCustomField ofSkuAndAssetKey(final String sku, final String assetKey, final String name, final Object value) {
        return ofSkuAndAssetKey(sku, assetKey, name, value, null);
    }

    public static SetAssetCustomField ofSkuAndAssetKey(final String sku, final String assetKey, final String name, final Object value, @Nullable final Boolean staged) {
        return ofSkuUsingJsonAndAssetKey(sku, assetKey, name, SphereJsonUtils.toJsonNode(value), staged);
    }

    @Nullable
    public Boolean getStaged() {
        return staged;
    }
}
