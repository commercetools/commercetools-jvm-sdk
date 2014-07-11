package io.sphere.sdk.products;

import com.google.common.base.Optional;

import java.util.List;

public class NewProductVariant {
    private final Optional<String> sku;
    private final List<Price> prices;
    private final List<Attribute> attributes;

    public NewProductVariant(final Optional<String> sku, final List<Price> prices, final List<Attribute> attributes) {
        this.sku = sku;
        this.prices = prices;
        this.attributes = attributes;
    }

    public Optional<String> getSku() {
        return sku;
    }

    public List<Price> getPrices() {
        return prices;
    }

    public List<Attribute> getAttributes() {
        return attributes;
    }
}
