package io.sphere.sdk.products;

import io.sphere.sdk.models.Base;
import io.sphere.sdk.models.Builder;
import io.sphere.sdk.attributes.Attribute;
import io.sphere.sdk.models.Image;

import java.util.*;

public final class ProductVariantBuilder extends Base implements Builder<ProductVariant> {
    private final int id;
    private Optional<String> sku = Optional.empty();
    private List<Price> prices = Collections.emptyList();
    private List<Attribute> attributes = Collections.emptyList();
    private List<Image> images = Collections.emptyList();
    private Optional<ProductVariantAvailability> availability = Optional.empty();

    private ProductVariantBuilder(final int id) {
        this.id = id;
    }

    public static ProductVariantBuilder of(final int id) {
        return new ProductVariantBuilder(id);
    }

    public static ProductVariantBuilder ofMasterVariant() {
        return of(1);
    }

    public ProductVariantBuilder sku(final Optional<String> sku) {
        this.sku = sku;
        return this;
    }

    public ProductVariantBuilder sku(final String sku) {
        Objects.requireNonNull(sku);
        return sku(Optional.of(sku));
    }

    public ProductVariantBuilder prices(final List<Price> prices) {
        this.prices = prices;
        return this;
    }

    public ProductVariantBuilder prices(final Price ... prices) {
        return prices(Arrays.asList(prices));
    }

    public ProductVariantBuilder price(final Price price) {
        return prices(Arrays.asList(price));
    }

    public ProductVariantBuilder attributes(final List<Attribute> attributes) {
        this.attributes = attributes;
        return this;
    }

    public ProductVariantBuilder attributes(final Attribute ... attributes) {
        return attributes(Arrays.asList(attributes));
    }

    public ProductVariantBuilder images(final List<Image> images) {
        this.images = images;
        return this;
    }

    public ProductVariantBuilder availability(final Optional<ProductVariantAvailability> availability) {
        this.availability = availability;
        return this;
    }

    public ProductVariantBuilder availability(final ProductVariantAvailability availability) {
        Objects.requireNonNull(availability);
        return availability(Optional.of(availability));
    }

    @Override
    public ProductVariant build() {
        return new ProductVariantImpl(id, sku, prices, attributes, images, availability);
    }
}
