package io.sphere.sdk.products;

import io.sphere.sdk.models.AssetDraft;
import io.sphere.sdk.products.attributes.AttributeDraft;
import io.sphere.sdk.products.attributes.NamedAttributeAccess;

import javax.annotation.Nullable;
import java.util.Collections;
import java.util.List;

import static io.sphere.sdk.utils.SphereInternalUtils.listOf;
import static java.util.Arrays.asList;

public final class ProductVariantDraftBuilder extends ProductVariantDraftBuilderBase<ProductVariantDraftBuilder> {

    ProductVariantDraftBuilder(@Nullable final List<AssetDraft> assets, @Nullable final List<AttributeDraft> attributes, @Nullable final List<Image> images, @Nullable final String key, @Nullable final List<PriceDraft> prices, @Nullable final String sku) {
        super(assets, attributes, images, key, prices, sku);
    }

    public ProductVariantDraftBuilder images(final Image image) {
        return images(Collections.singletonList(image));
    }

    public ProductVariantDraftBuilder prices(final PriceDraft ... prices) {
        return prices(asList(prices));
    }

    public ProductVariantDraftBuilder price(final PriceDraft price) {
        return prices(Collections.singletonList(price));
    }

    public ProductVariantDraftBuilder attributes(final AttributeDraft ... attributes) {
        return attributes(asList(attributes));
    }

    public <T> ProductVariantDraftBuilder plusAttribute(final NamedAttributeAccess<T> namedAccess, final T value) {
        return plusAttribute(AttributeDraft.of(namedAccess, value));
    }

    public <T> ProductVariantDraftBuilder plusAttribute(final String name, final T value) {
        return plusAttribute(AttributeDraft.of(name, value));
    }

    public ProductVariantDraftBuilder plusAttribute(final AttributeDraft attribute) {
        return attributes != null ? attributes(listOf(attributes, attribute)) : attributes(attribute);
    }
}
