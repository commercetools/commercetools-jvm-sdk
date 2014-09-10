package io.sphere.sdk.products;

import com.fasterxml.jackson.annotation.JsonCreator;
import io.sphere.sdk.models.Base;
import io.sphere.sdk.products.exceptions.AttributeMappingException;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static java.lang.String.format;

class ProductVariantImpl extends Base implements ProductVariant {
    private final long id;
    private final Optional<String> sku;
    private final List<Price> prices;
    private final List<Attribute> attributes;

    @JsonCreator
    ProductVariantImpl(final long id, final Optional<String> sku, final List<Price> prices, final List<Attribute> attributes) {
        this.id = id;
        this.sku = sku;
        this.prices = prices;
        this.attributes = attributes;
    }

    @Override
    public long getId() {
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
    public List<Attribute> getAttributes() {
        return attributes;
    }

    @Override
    public <T> Optional<T> getAttribute(final AttributeGetter<Product, T> accessor) {
        final String attributeName = accessor.getName();
        final Optional<Attribute> attributeOption = getAttributes().stream()
                .filter(a -> Objects.equals(attributeName, a.getName()))
                .findFirst();
        return attributeOption.map(attribute -> {
            final AttributeMapper<T> mapper = accessor.getMapper();
            try {
                return attribute.getValue(mapper);
            } catch (final AttributeMappingException e) {
                throw new AttributeMappingException(format("ProductVariant(id=%s)", id), attributeName, mapper, e.getCause());
            }
        });
    }
}
