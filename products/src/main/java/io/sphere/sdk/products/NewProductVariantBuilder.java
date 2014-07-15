package io.sphere.sdk.products;

import java.util.Optional;
import io.sphere.sdk.models.Base;
import io.sphere.sdk.models.Builder;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public final class NewProductVariantBuilder extends Base implements Builder<NewProductVariant> {
    private Optional<String> sku = Optional.empty();

    private List<Price> prices = Collections.emptyList();

    private List<Attribute> attributes = Collections.emptyList();

    private NewProductVariantBuilder() {
    }

    public static NewProductVariantBuilder of() {
        return new NewProductVariantBuilder();
    }

    public NewProductVariantBuilder sku(final Optional<String> sku) {
        this.sku = sku;
        return this;
    }

    public NewProductVariantBuilder sku(final String sku) {
        return sku(Optional.ofNullable(sku));
    }

    public NewProductVariantBuilder prices(final List<Price> prices) {
        this.prices = prices;
        return this;
    }

    public NewProductVariantBuilder prices(final Price ... prices) {
        return prices(Arrays.asList(prices));
    }

    public NewProductVariantBuilder price(final Price price) {
        return prices(Arrays.asList(price));
    }

    public NewProductVariantBuilder attributes(final List<Attribute> attributes) {
        this.attributes = attributes;
        return this;
    }

    public NewProductVariantBuilder attributes(final Attribute ... attributes) {
        return attributes(Arrays.asList(attributes));
    }

    @Override
    public NewProductVariant build() {
        return new NewProductVariantImpl(sku, prices, attributes);
    }
}
