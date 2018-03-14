package io.sphere.sdk.products.commands.updateactions;

import com.fasterxml.jackson.databind.JsonNode;
import io.sphere.sdk.models.ResourceIdentifier;
import io.sphere.sdk.products.Product;
import io.sphere.sdk.types.CustomFieldsDraft;
import io.sphere.sdk.types.customupdateactions.SetCustomTypeBase;

import javax.annotation.Nullable;
import java.util.Map;
import java.util.Optional;

/**
 * This action sets, overwrites or removes the custom type and fields for an existing Asset.
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
public final class SetAssetCustomType extends SetCustomTypeBase<Product> {
    @Nullable
    private final Integer variantId;
    @Nullable
    private final String sku;
    private final String assetId;
    private final String assetKey;
    @Nullable
    private final Boolean staged;

    private SetAssetCustomType(final String assetId,final String assetKey, @Nullable final Integer variantId, @Nullable final String sku, @Nullable final String typeId, @Nullable final String typeKey, @Nullable final Map<String, JsonNode> fields, final Boolean staged) {
        super("setAssetCustomType", typeId, typeKey, fields);
        this.assetId = assetId;
        this.variantId = variantId;
        this.sku = sku;
        this.staged = staged;
        this.assetKey = assetKey;
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

    private static SetAssetCustomType of(final String assetId,final String assetKey, @Nullable final Integer variantId, @Nullable final String sku, @Nullable final CustomFieldsDraft customFieldsDraft) {
        return of(assetId,assetKey , variantId, sku, customFieldsDraft, null);
    }

    private static SetAssetCustomType of(final String assetId,final String assetKey, @Nullable final Integer variantId, @Nullable final String sku, @Nullable final CustomFieldsDraft customFieldsDraft, @Nullable final Boolean staged) {
        final Optional<CustomFieldsDraft> draft = Optional.ofNullable(customFieldsDraft);
        final String typeId = draft.map(CustomFieldsDraft::getType).map(ResourceIdentifier::getId).orElse(null);
        final String typeKey = draft.map(CustomFieldsDraft::getType).map(ResourceIdentifier::getKey).orElse(null);
        final Map<String, JsonNode> fields = draft.map(CustomFieldsDraft::getFields).orElse(null);
        return new SetAssetCustomType(assetId,assetKey, variantId, sku, typeId, typeKey, fields, staged);

    }

    public static SetAssetCustomType ofVariantId(final Integer variantId, final String assetId, @Nullable final CustomFieldsDraft customFieldsDraft) {
        return ofVariantId(variantId, assetId, customFieldsDraft, null);
    }

    public static SetAssetCustomType ofVariantId(final Integer variantId, final String assetId, @Nullable final CustomFieldsDraft customFieldsDraft, @Nullable final Boolean staged) {
        return of(assetId,null, variantId, null, customFieldsDraft, staged);
    }

    public static SetAssetCustomType ofSku(final String sku, final String assetId, @Nullable final CustomFieldsDraft customFieldsDraft) {
        return ofSku(sku, assetId, customFieldsDraft, null);
    }

    public static SetAssetCustomType ofSku(final String sku, final String assetId, @Nullable final CustomFieldsDraft customFieldsDraft, @Nullable final Boolean staged) {
        return of(assetId, null, null, sku, customFieldsDraft, staged);
    }




    public static SetAssetCustomType ofVariantIdAndAssetKey(final Integer variantId, final String assetKey, @Nullable final CustomFieldsDraft customFieldsDraft) {
        return ofVariantIdAndAssetKey(variantId, assetKey, customFieldsDraft, null);
    }

    public static SetAssetCustomType ofVariantIdAndAssetKey(final Integer variantId, final String assetKey, @Nullable final CustomFieldsDraft customFieldsDraft, @Nullable final Boolean staged) {
        return of(null,assetKey, variantId, null, customFieldsDraft, staged);
    }

    public static SetAssetCustomType ofSkuAndAssetKey(final String sku, final String assetKey, @Nullable final CustomFieldsDraft customFieldsDraft) {
        return ofSkuAndAssetKey(sku, assetKey, customFieldsDraft, null);
    }

    public static SetAssetCustomType ofSkuAndAssetKey(final String sku, final String assetKey, @Nullable final CustomFieldsDraft customFieldsDraft, @Nullable final Boolean staged) {
        return of(null, assetKey, null, sku, customFieldsDraft, staged);
    }

    @Nullable
    public Boolean getStaged() {
        return staged;
    }
}
