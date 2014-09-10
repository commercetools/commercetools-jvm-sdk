package io.sphere.sdk.products;

import io.sphere.sdk.models.Base;
import io.sphere.sdk.models.Builder;
import io.sphere.sdk.attributes.Attribute;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public final class ProductVariantBuilder extends Base implements Builder<ProductVariant> {
    private final long id;
    private Optional<String> sku = Optional.empty();

    private List<Price> prices = Collections.emptyList();

    private List<Attribute> attributes = Collections.emptyList();

    private ProductVariantBuilder(final long id) {
        this.id = id;
    }

    public static ProductVariantBuilder of(final long id) {
        return new ProductVariantBuilder(id);
    }

    public ProductVariantBuilder sku(final Optional<String> sku) {
        this.sku = sku;
        return this;
    }

    public ProductVariantBuilder sku(final String sku) {
        return sku(Optional.ofNullable(sku));
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

    @Override
    public ProductVariant build() {
        return new ProductVariantImpl(id, sku, prices, attributes);
    }
}
