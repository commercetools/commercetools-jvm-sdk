package io.sphere.sdk.products.commands.updateactions;

import io.sphere.sdk.models.Image;
import io.sphere.sdk.products.ProductUpdateScope;

/**
 * Removes a product image.
 * Also deletes it from the Content Delivery Network (it would not be deleted from the CDN in case of external image).
 * Deletion from the CDN is not instant, which means the image file itself will stay available for some time after the deletion.
 *
 * {@include.example io.sphere.sdk.products.ProductCrudIntegrationTest#addExternalImageUpdateAction()}
 */
public class RemoveImage extends StageableProductUpdateAction {
    private final long variantId;
    private final String imageUrl;

    private RemoveImage(final String imageUrl, final long variantId, final ProductUpdateScope productUpdateScope) {
        super("removeImage", productUpdateScope);
        this.imageUrl = imageUrl;
        this.variantId = variantId;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public long getVariantId() {
        return variantId;
    }

    public static RemoveImage of(final Image image, final long variantId, final ProductUpdateScope productUpdateScope) {
        return of(image.getUrl(), variantId, productUpdateScope);
    }

    public static RemoveImage of(final String imageUrl, final long variantId, final ProductUpdateScope productUpdateScope) {
        return new RemoveImage(imageUrl, variantId, productUpdateScope);
    }
}
