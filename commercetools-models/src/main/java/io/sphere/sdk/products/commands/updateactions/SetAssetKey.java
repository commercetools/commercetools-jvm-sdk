package io.sphere.sdk.products.commands.updateactions;

import io.sphere.sdk.products.Product;

import javax.annotation.Nullable;

/**
 * Adds asset to a {@link io.sphere.sdk.products.Product}.
 */
public final class SetAssetKey extends StagedProductUpdateActionImpl<Product> {

  @Nullable
  private final Integer variantId;
  @Nullable
  private final String sku;

  private final String assetId;
  private final String assetKey;

  private SetAssetKey(final String assetId,final String assetKey, @Nullable final Integer variantId, @Nullable final String sku, @Nullable final Boolean staged) {
    super("removeAsset", staged);
    this.assetId = assetId;
    this.assetKey = assetKey;
    this.variantId = variantId;
    this.sku = sku;
  }

  public String getAssetId() {
    return assetId;
  }

  @Nullable
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


  public static SetAssetKey ofSku(final String assetId, final String sku, @Nullable final String assetKey) {
    return new SetAssetKey(assetId, assetKey,null,sku, null);
  }

  public static SetAssetKey ofVariantId(final String assetId, final Integer variantId, @Nullable final String assetKey) {
    return new SetAssetKey(assetId, assetKey,variantId,null, null);
  }

  public SetAssetKey withStaged(@Nullable final Boolean staged){
    return new SetAssetKey(getAssetId(),getAssetKey(),getVariantId(),getSku(),staged);
  }

  public SetAssetKey withAssetKey(@Nullable final String assetKey){
    return new SetAssetKey(getAssetId(),assetKey ,getVariantId(),getSku(),isStaged());
  }

}
