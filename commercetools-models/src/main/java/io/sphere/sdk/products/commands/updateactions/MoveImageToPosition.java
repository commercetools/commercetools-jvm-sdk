package io.sphere.sdk.products.commands.updateactions;

import io.sphere.sdk.commands.UpdateActionImpl;
import io.sphere.sdk.products.Product;
import io.sphere.sdk.products.ProductVariant;

/**
 *  Moves an image to a new position within a product variant.
 *
 *  {@doc.gen intro}
 *
 *  {@include.example io.sphere.sdk.products.commands.ProductUpdateCommandIntegrationTest#moveImageToPosition()}
 *
 *  @see ProductVariant#getImages()
 */
public final class MoveImageToPosition extends UpdateActionImpl<Product> {
    private final Integer variantId;
    private final String imageUrl;
    private final Integer position;


    private MoveImageToPosition(final Integer position, final String imageUrl, final Integer variantId) {
        super("moveImageToPosition");
        this.position = position;
        this.imageUrl = imageUrl;
        this.variantId = variantId;
    }

    public static MoveImageToPosition of(final String imageUrl, final Integer variantId, final Integer position) {
        return new MoveImageToPosition(position, imageUrl, variantId);
    }

    public Integer getVariantId() {
        return variantId;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public Integer getPosition() {
        return position;
    }
}
