package io.sphere.sdk.products.commands.updateactions;

import io.sphere.sdk.commands.UpdateActionImpl;
import io.sphere.sdk.products.Image;
import io.sphere.sdk.products.Product;
import io.sphere.sdk.products.ProductVariant;

/**
 *  Adds external image url with meta-information to the product variant.
 *
 *  {@doc.gen intro}
 *
 *  {@include.example io.sphere.sdk.products.commands.ProductUpdateCommandIntegrationTest#addExternalImage()}
 *
 *  @see ProductVariant#getImages()
 *  @see RemoveImage
 */
public final class AddExternalImage extends UpdateActionImpl<Product> {
    private final Integer variantId;
    private final Image image;

    private AddExternalImage(final Image image, final Integer variantId) {
        super("addExternalImage");
        this.image = image;
        this.variantId = variantId;
    }

    public static AddExternalImage of(final Image image, final Integer variantId) {
        return new AddExternalImage(image, variantId);
    }

    public Image getImage() {
        return image;
    }

    public Integer getVariantId() {
        return variantId;
    }
}
