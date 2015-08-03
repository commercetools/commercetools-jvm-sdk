package io.sphere.sdk.products.commands.updateactions;

import io.sphere.sdk.commands.UpdateAction;
import io.sphere.sdk.products.Image;
import io.sphere.sdk.products.Product;

/**
 *  Adds external image url with meta-information to the product variant.
 *
 * {@include.example io.sphere.sdk.products.commands.ProductUpdateCommandTest#addExternalImage()}
 */
public class AddExternalImage extends UpdateAction<Product> {
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
