package io.sphere.sdk.products.commands.updateactions;

import io.sphere.sdk.products.Image;
import io.sphere.sdk.products.PriceDraft;
import io.sphere.sdk.products.Product;
import io.sphere.sdk.products.ProductData;
import io.sphere.sdk.products.attributes.AttributeDraft;

import javax.annotation.Nullable;
import java.util.List;

/**
 * Adds a variant to a product.
 *
 * {@doc.gen intro}
 *
 * {@include.example io.sphere.sdk.products.commands.ProductUpdateCommandIntegrationTest#addVariant()}
 *
 * @see io.sphere.sdk.products.commands.updateactions.RemoveVariant
 * @see ProductData#getAllVariants()
 * @see ProductData#getMasterVariant()
 * @see ProductData#getVariants()
 * @see io.sphere.sdk.products.ProductProjection#getAllVariants()
 * @see io.sphere.sdk.products.ProductProjection#getMasterVariant()
 * @see io.sphere.sdk.products.ProductProjection#getVariants()
 */
public final class AddVariant extends StagedProductUpdateActionImpl<Product> {
    @Nullable
    private final String sku;
    private final List<PriceDraft> prices;
    private final List<AttributeDraft> attributes;
    @Nullable
    private final String key;
    @Nullable
    private final List<Image> images;

    private AddVariant(final List<AttributeDraft> attributes, final List<PriceDraft> prices, @Nullable final String sku, final String key, final List<Image> images, @Nullable final Boolean staged) {
        super("addVariant", staged);
        this.attributes = attributes;
        this.prices = prices;
        this.sku = sku;
        this.key = key;
        this.images = images;
    }

    public List<AttributeDraft> getAttributes() {
        return attributes;
    }

    public List<PriceDraft> getPrices() {
        return prices;
    }

    @Nullable
    public String getSku() {
        return sku;
    }

    @Nullable
    public String getKey() {
        return key;
    }

    @Nullable
    public List<Image> getImages() {
        return images;
    }

    public AddVariant withSku(@Nullable final String sku) {
        return new AddVariant(attributes, prices, sku, key, images, staged);
    }

    public AddVariant withKey(@Nullable final String key) {
        return new AddVariant(attributes, prices, sku, key, images, staged);
    }

    public AddVariant withImages(final List<Image> images) {
        return new AddVariant(attributes, prices, sku, key, images, staged);
    }

    public static AddVariant of(final List<AttributeDraft> attributes, final List<PriceDraft> prices, @Nullable final String sku) {
        return of(attributes, prices, sku, null);
    }

    public static AddVariant of(final List<AttributeDraft> attributes, final List<PriceDraft> prices, @Nullable final String sku, @Nullable final Boolean staged) {
        return new AddVariant(attributes, prices, sku, null, null, staged);
    }

    public static AddVariant of(final List<AttributeDraft> attributes, final List<PriceDraft> prices) {
        return of(attributes, prices, null, null);
    }

    public static AddVariant of(final List<AttributeDraft> attributes, final List<PriceDraft> prices, @Nullable final Boolean staged) {
        return new AddVariant(attributes, prices, null, null, null, staged);
    }
}
