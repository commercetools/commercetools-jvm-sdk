package io.sphere.sdk.orders;

import io.sphere.sdk.attributes.Attribute;
import io.sphere.sdk.models.Base;
import io.sphere.sdk.models.Builder;
import io.sphere.sdk.models.Image;
import io.sphere.sdk.products.Price;

import java.util.List;
import java.util.Optional;

public class ImportProductVariantBuilder extends Base implements Builder<ImportProductVariant> {
    private Optional<Integer> id = Optional.empty();
    private Optional<String> sku = Optional.empty();
    private Optional<List<Price>> prices = Optional.empty();
    private Optional<List<Attribute>> attributes = Optional.empty();
    private Optional<List<Image>> images = Optional.empty();

    private ImportProductVariantBuilder() {
    }

    public ImportProductVariantBuilder id(final Optional<Integer> id) {
        this.id = id;
        return this;
    }

    public ImportProductVariantBuilder id(final Integer id) {
        return id(Optional.of(id));
    }

    public ImportProductVariantBuilder sku(final Optional<String> sku) {
        this.sku = sku;
        return this;
    }

    public ImportProductVariantBuilder sku(final String sku) {
        return sku(Optional.of(sku));
    }

    public ImportProductVariantBuilder prices(final Optional<List<Price>> prices) {
        this.prices = prices;
        return this;
    }

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
        return new ImportProductVariantBuilder().sku(sku);
    }

    public static ImportProductVariantBuilder ofVariantId(final int variantId) {
        return new ImportProductVariantBuilder().id(variantId);
    }

    @Override
    public ImportProductVariant build() {
        return new ImportProductVariantImpl(id, sku, prices, images, attributes);
    }
}
