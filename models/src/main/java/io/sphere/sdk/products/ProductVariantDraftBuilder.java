package io.sphere.sdk.products;

import java.util.Optional;
import io.sphere.sdk.models.Base;
import io.sphere.sdk.models.Builder;
import io.sphere.sdk.attributes.Attribute;
import io.sphere.sdk.utils.ListUtils;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public final class ProductVariantDraftBuilder extends Base implements Builder<ProductVariantDraft> {
    private Optional<String> sku = Optional.empty();

    private List<Price> prices = Collections.emptyList();

    private List<Attribute> attributes = Collections.emptyList();

    private ProductVariantDraftBuilder() {
    }

    public static ProductVariantDraftBuilder of() {
        return new ProductVariantDraftBuilder();
    }

    public ProductVariantDraftBuilder sku(final Optional<String> sku) {
        this.sku = sku;
        return this;
    }

    public ProductVariantDraftBuilder sku(final String sku) {
        return sku(Optional.ofNullable(sku));
    }

    public ProductVariantDraftBuilder prices(final List<Price> prices) {
        this.prices = prices;
        return this;
    }

    public ProductVariantDraftBuilder prices(final Price ... prices) {
        return prices(Arrays.asList(prices));
    }

    public ProductVariantDraftBuilder price(final Price price) {
        return prices(Arrays.asList(price));
    }

    public ProductVariantDraftBuilder attributes(final List<Attribute> attributes) {
        this.attributes = attributes;
        return this;
    }

    public ProductVariantDraftBuilder attributes(final Attribute ... attributes) {
        return attributes(Arrays.asList(attributes));
    }

    public ProductVariantDraftBuilder plusAttribute(final Attribute attribute) {
        return attributes(ListUtils.listOf(attributes, attribute));
    }

    @Override
    public ProductVariantDraft build() {
        return new ProductVariantDraftImpl(sku, prices, attributes);
    }
}
