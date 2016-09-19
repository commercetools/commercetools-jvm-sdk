package io.sphere.sdk.products.commands.updateactions;

import io.sphere.sdk.commands.UpdateActionImpl;
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
 * {@include.example io.sphere.sdk.products.commands.ProductUpdateCommandIntegrationTest#removeImage()}
 */
public final class RemoveImage extends UpdateActionImpl<Product> {
    @Nullable
    private final Integer variantId;
    @Nullable
    private final String sku;
    private final String imageUrl;

    private RemoveImage(final String imageUrl, final Integer variantId, final String sku) {
        super("removeImage");
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
        return ofVariantId(image.getUrl(), variantId);
    }

    public static RemoveImage ofVariantId(final Image image, final Integer variantId) {
        return ofVariantId(image.getUrl(), variantId);
    }

    public static RemoveImage ofSku(final Image image, final String sku) {
        return ofSku(image.getUrl(), sku);
    }

    public static RemoveImage ofVariantId(final String imageUrl, final Integer variantId) {
        return new RemoveImage(imageUrl, variantId, null);
    }

    public static RemoveImage ofSku(final String imageUrl, final String sku) {
        return new RemoveImage(imageUrl, null, sku);
    }
}
