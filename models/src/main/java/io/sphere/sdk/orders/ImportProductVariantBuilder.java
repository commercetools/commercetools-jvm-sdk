package io.sphere.sdk.orders;

import io.sphere.sdk.attributes.Attribute;
import io.sphere.sdk.models.Base;
import io.sphere.sdk.models.Builder;
import io.sphere.sdk.models.Image;
import io.sphere.sdk.products.Price;

import java.util.List;
import java.util.Optional;

public class ImportProductVariantBuilder extends Base implements Builder<ImportProductVariant> {
    private final Optional<Integer> id;
    private final Optional<String> sku;
    private final Optional<String> productId;
    private Optional<List<Price>> prices = Optional.empty();
    private Optional<List<Attribute>> attributes = Optional.empty();
    private Optional<List<Image>> images = Optional.empty();

    private ImportProductVariantBuilder(final Optional<String> sku, final Optional<String> productId, final Optional<Integer> id) {
        this.sku = sku;
        this.productId = productId;
        this.id = id;
    }

    public ImportProductVariantBuilder prices(final Optional<List<Price>> prices) {
        this.prices = prices;
        return this;
    }

    /**
     * The prices of the variant. The prices should not contain two prices for the same price scope (same currency, country and customer group). If this property is defined, then it will override the prices property from the original product variant, otherwise prices property from the original product variant would be copied in the resulting order.
     * @param prices
     * @return
     */
    public ImportProductVariantBuilder prices(final List<Price> prices) {
        return prices(Optional.of(prices));
    }

    public ImportProductVariantBuilder attributes(final Optional<List<Attribute>> attributes) {
        this.attributes = attributes;
        return this;
    }

    public ImportProductVariantBuilder attributes(final List<Attribute> attributes) {
        return attributes(Optional.of(attributes));
    }

    public ImportProductVariantBuilder images(final Optional<List<Image>> images) {
        this.images = images;
        return this;
    }

    public ImportProductVariantBuilder images(final List<Image> images) {
        return images(Optional.of(images));
    }

    public static ImportProductVariantBuilder ofSku(final String sku) {
        return new ImportProductVariantBuilder(Optional.of(sku), Optional.<String>empty(), Optional.<Integer>empty());
    }

    public static ImportProductVariantBuilder of(final String productId, final int variantId, final String sku) {
        return new ImportProductVariantBuilder(Optional.of(sku), Optional.of(productId), Optional.of(variantId));
    }

    public static ImportProductVariantBuilder of(final String productId, final int variantId) {
        return new ImportProductVariantBuilder(Optional.<String>empty(), Optional.of(productId), Optional.of(variantId));
    }

    @Override
    public ImportProductVariant build() {
        return new ImportProductVariantImpl(id, sku, prices, images, attributes, productId);
    }
}
