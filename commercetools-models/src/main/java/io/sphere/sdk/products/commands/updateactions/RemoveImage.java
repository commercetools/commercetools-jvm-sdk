package io.sphere.sdk.products.commands.updateactions;

import io.sphere.sdk.commands.UpdateActionImpl;
import io.sphere.sdk.products.Image;
import io.sphere.sdk.products.Product;

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
    private final Integer variantId;
    private final String imageUrl;

    private RemoveImage(final String imageUrl, final Integer variantId) {
        super("removeImage");
        this.imageUrl = imageUrl;
        this.variantId = variantId;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public Integer getVariantId() {
        return variantId;
    }

    public static RemoveImage of(final Image image, final Integer variantId) {
        return of(image.getUrl(), variantId);
    }

    public static RemoveImage of(final String imageUrl, final Integer variantId) {
        return new RemoveImage(imageUrl, variantId);
    }
}
