package io.sphere.sdk.orders;

import io.sphere.sdk.attributes.Attribute;
import io.sphere.sdk.models.Base;
import io.sphere.sdk.models.Image;
import io.sphere.sdk.products.Price;

import java.util.List;
import java.util.Optional;

final class ImportProductVariantImpl extends Base implements ImportProductVariant {
    private final Optional<Integer> id;
    private final Optional<String> sku;
    private final Optional<List<Price>> prices;
    private final Optional<List<Attribute>> attributes;
    private final Optional<List<Image>> images;

    ImportProductVariantImpl(final Optional<Integer> id, final Optional<String> sku, final Optional<List<Price>> prices, final Optional<List<Image>> images, final Optional<List<Attribute>> attributes) {
        this.attributes = attributes;
        this.id = id;
        this.sku = sku;
        this.prices = prices;
        this.images = images;
    }

    @Override
    public Optional<List<Attribute>> getAttributes() {
        return attributes;
    }

    @Override
    public Optional<Integer> getId() {
        return id;
    }

    @Override
    public Optional<List<Image>> getImages() {
        return images;
    }

    @Override
    public Optional<List<Price>> getPrices() {
        return prices;
    }

    @Override
    public Optional<String> getSku() {
        return sku;
    }
}
