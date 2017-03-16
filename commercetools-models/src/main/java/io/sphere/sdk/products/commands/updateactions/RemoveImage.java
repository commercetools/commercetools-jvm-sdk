package io.sphere.sdk.products.commands.updateactions;

import io.sphere.sdk.products.Image;
import io.sphere.sdk.products.Product;

import javax.annotation.Nullable;

/**
 * Removes a product image.
 * Also deletes it from the Content Delivery Network (it would not be deleted from the CDN in case of external image).
 * Deletion from the CDN is not instant, which means the image file itself will stay available for some time after the deletion.
 *
 * {@doc.gen intro}
 *
 * <p>By variant ID (every variant has a variantId):</p>
 * {@include.example io.sphere.sdk.products.commands.ProductUpdateCommandIntegrationTest#removeImageByVariantId()}
 *
 * <p>By SKU (attention, SKU is optional field in a variant):</p>
 * {@include.example io.sphere.sdk.products.commands.ProductUpdateCommandIntegrationTest#removeImageBySku()}
 */
public final class RemoveImage extends StagedProductUpdateActionImpl<Product> {
    @Nullable
    private final Integer variantId;
    @Nullable
    private final String sku;
    private final String imageUrl;

    private RemoveImage(final String imageUrl, @Nullable final Integer variantId, @Nullable final String sku, @Nullable final Boolean staged) {
        super("removeImage", staged);
        this.imageUrl = imageUrl;
        this.variantId = variantId;
        this.sku = sku;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    @Nullable
    public Integer getVariantId() {
        return variantId;
    }

    @Nullable
    public String getSku() {
        return sku;
    }

    public static RemoveImage of(final Image image, final Integer variantId) {
        return of(image.getUrl(), variantId);
    }

    public static RemoveImage of(final String imageUrl, final Integer variantId) {
        return ofVariantId(variantId, imageUrl);
    }

    public static RemoveImage ofVariantId(final Integer variantId, final Image image) {
        return ofVariantId(variantId, image.getUrl(), null);
    }

    public static RemoveImage ofVariantId(final Integer variantId, final String imageUrl) {
        return ofVariantId(variantId, imageUrl, null);
    }

    public static RemoveImage ofVariantId(final Integer variantId, final Image image, @Nullable final Boolean staged) {
        return ofVariantId(variantId, image.getUrl(), staged);
    }

    public static RemoveImage ofVariantId(final Integer variantId, final String imageUrl, @Nullable final Boolean staged) {
        return new RemoveImage(imageUrl, variantId, null, staged);
    }

    public static RemoveImage ofSku(final String sku, final Image image) {
        return ofSku(sku, image.getUrl(), null);
    }

    public static RemoveImage ofSku(final String sku, final Image image, @Nullable final Boolean staged) {
        return ofSku(sku, image.getUrl(), staged);
    }

    public static RemoveImage ofSku(final String sku, final String imageUrl) {
        return ofSku(sku, imageUrl, null);
    }

    public static RemoveImage ofSku(final String sku, final String imageUrl, @Nullable final Boolean staged) {
        return new RemoveImage(imageUrl, null, sku, staged);
    }
}
