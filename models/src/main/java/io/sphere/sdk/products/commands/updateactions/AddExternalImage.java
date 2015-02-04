package io.sphere.sdk.products.commands.updateactions;

import io.sphere.sdk.models.Image;
import io.sphere.sdk.products.ProductUpdateScope;


/**
 *  Adds external image url with meta-information to the product variant.
 *
 * {@include.example io.sphere.sdk.products.commands.ProductUpdateCommandTest#addExternalImage()}
 */
public class AddExternalImage extends StageableProductUpdateAction {
    private final int variantId;
    private final Image image;

    private AddExternalImage(final Image image, final int variantId, final ProductUpdateScope productUpdateScope) {
        super("addExternalImage", productUpdateScope);
        this.image = image;
        this.variantId = variantId;
    }

    public static AddExternalImage of(final Image image, final int variantId, final ProductUpdateScope productUpdateScope) {
        return new AddExternalImage(image, variantId, productUpdateScope);
    }

    public Image getImage() {
        return image;
    }

    public int getVariantId() {
        return variantId;
    }
}
