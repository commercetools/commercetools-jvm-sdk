package io.sphere.sdk.products;

import java.util.Optional;

import com.fasterxml.jackson.databind.JsonNode;
import io.sphere.sdk.attributes.AttributeDraft;
import io.sphere.sdk.attributes.NamedAttributeAccess;
import io.sphere.sdk.models.Base;
import io.sphere.sdk.models.Builder;
import io.sphere.sdk.utils.ListUtils;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public final class ProductVariantDraftBuilder extends Base implements Builder<ProductVariantDraft> {
    private Optional<String> sku = Optional.empty();

    private List<Price> prices = Collections.emptyList();

    private List<AttributeDraft> attributes = Collections.emptyList();

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

    public ProductVariantDraftBuilder attributes(final List<AttributeDraft> attributes) {
        this.attributes = attributes;
        return this;
    }

    public ProductVariantDraftBuilder attributes(final AttributeDraft ... attributes) {
        return attributes(Arrays.asList(attributes));
    }

    public <T> ProductVariantDraftBuilder plusAttribute(final NamedAttributeAccess<T> namedAccess, final T value) {
        return plusAttribute(AttributeDraft.of(namedAccess, value));
    }

    public <T> ProductVariantDraftBuilder plusAttribute(final String name, final T value) {
        return plusAttribute(AttributeDraft.of(name, value));
    }

    public ProductVariantDraftBuilder plusAttribute(final AttributeDraft attribute) {
        return attributes(ListUtils.listOf(attributes, attribute));
    }

    @Override
    public ProductVariantDraft build() {
        return new ProductVariantDraftImpl(sku, prices, attributes);
    }
}
