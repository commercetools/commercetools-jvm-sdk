package io.sphere.sdk.products.commands.updateactions;

import io.sphere.sdk.commands.UpdateActionImpl;
import io.sphere.sdk.products.Image;
import io.sphere.sdk.products.Product;
import io.sphere.sdk.products.ProductVariant;

import javax.annotation.Nullable;

/**
 *  Adds external image url with meta-information to the product variant.
 *
 *  {@doc.gen intro}
 *
 *  {@include.example io.sphere.sdk.products.commands.ProductUpdateCommandIntegrationTest#addExternalImage()}
 *
 *  <p>By variant ID (every variant has a variantId):</p>
 * {@include.example io.sphere.sdk.products.commands.ProductUpdateCommandIntegrationTest#addExternalImageByVariantId()}
 *
 * <p>By SKU (attention, SKU is optional field in a variant):</p>
 * {@include.example io.sphere.sdk.products.commands.ProductUpdateCommandIntegrationTest#addExternalImageBySku()}
 *
 *  @see ProductVariant#getImages()
 *  @see RemoveImage
 */
public final class AddExternalImage extends UpdateActionImpl<Product> {
    @Nullable
    private final Integer variantId;
    @Nullable
    private final String sku;
    private final Image image;

    private AddExternalImage(final Image image, @Nullable final Integer variantId, @Nullable final String sku) {
        super("addExternalImage");
        this.image = image;
        this.variantId = variantId;
        this.sku = sku;
    }

    public static AddExternalImage of(final Image image, final Integer variantId) {
        return new AddExternalImage(image, variantId, null);
    }

    public static AddExternalImage ofVariantId(final Image image, final Integer variantId) {
        return new AddExternalImage(image, variantId, null);
    }

    public static AddExternalImage ofSku(final Image image, final String sku) {
        return new AddExternalImage(image, null, sku);
    }

    public Image getImage() {
        return image;
    }

    @Nullable
    public Integer getVariantId() {
        return variantId;
    }

    @Nullable
    public String getSku() {
        return sku;
    }
}
