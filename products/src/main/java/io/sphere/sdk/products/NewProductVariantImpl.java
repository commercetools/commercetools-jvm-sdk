package io.sphere.sdk.products;

import java.util.Optional;
import io.sphere.sdk.models.Base;
import io.sphere.sdk.attributes.Attribute;

import java.util.List;

class NewProductVariantImpl extends Base implements NewProductVariant {
    private final Optional<String> sku;
    private final List<Price> prices;
    private final List<Attribute> attributes;

    public NewProductVariantImpl(final Optional<String> sku, final List<Price> prices, final List<Attribute> attributes) {
        this.sku = sku;
        this.prices = prices;
        this.attributes = attributes;
    }

    @Override
    public Optional<String> getSku() {
        return sku;
    }

    @Override
    public List<Price> getPrices() {
        return prices;
    }

    @Override
    public List<Attribute> getAttributes() {
        return attributes;
    }
}
