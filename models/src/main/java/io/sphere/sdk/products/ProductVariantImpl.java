package io.sphere.sdk.products;

import com.fasterxml.jackson.annotation.JsonCreator;
import io.sphere.sdk.json.JsonException;
import io.sphere.sdk.models.Base;
import io.sphere.sdk.attributes.Attribute;
import io.sphere.sdk.attributes.AttributeGetter;
import io.sphere.sdk.attributes.AttributeMapper;
import io.sphere.sdk.models.Image;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static java.lang.String.format;

class ProductVariantImpl extends AttributeContainerImpl implements ProductVariant {
    private final int id;
    private final Optional<String> sku;
    private final List<Price> prices;
    private final List<Image> images;
    private final Optional<ProductVariantAvailability> availability;

    @JsonCreator
    ProductVariantImpl(final int id, final Optional<String> sku, final List<Price> prices,
                       final List<Attribute> attributes, final List<Image> images,
                       final Optional<ProductVariantAvailability> availability) {
        super(attributes);

        this.id = id;
        this.sku = sku;
        this.prices = prices;
        this.images = images;
        this.availability = availability;
    }

    @Override
    public int getId() {
        return id;
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
    public List<Image> getImages() {
        return images;
    }

    @Override
    public Optional<ProductVariantAvailability> getAvailability() {
        return availability;
    }

    @Override
    protected JsonException transformError(JsonException e, String attributeName, AttributeMapper<?> mapper) {
        return enrich(format("ProductVariant(id=%s)", id), attributeName, mapper, e.getCause());
    }

    private JsonException enrich(final Object objectWithAttributes, final String attributeName, final AttributeMapper<?> mapper, final Throwable cause) {
        return new JsonException(format("%s does not contain an attribute '%s' which can be mapped with %s.", objectWithAttributes, attributeName, mapper), cause);
    }
}
