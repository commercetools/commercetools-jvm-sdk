package io.sphere.sdk.products;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.sphere.sdk.attributes.Attribute;
import io.sphere.sdk.attributes.AttributeGetter;
import io.sphere.sdk.models.Image;

import java.util.List;
import java.util.Optional;

import static java.util.Objects.requireNonNull;

@JsonDeserialize(as = ProductVariantImpl.class)
public interface ProductVariant {

    public int getId();

    public Optional<String> getSku();

    public List<Price> getPrices();

    public List<Attribute> getAttributes();

    /**
     * Access one attribute of a specific name and type which is known in the first place, consult {@link io.sphere.sdk.attributes.AttributeGetterSetter} how to implement these.
     *
     * @throws io.sphere.sdk.json.JsonException if the type of attribute cannot be parsed
     *
     * @param accessor declaration of the name and type of the attribute
     * @param <T> the underlying type of the attribute
     * @return the value of the attribute, or Optional.empty if absent
     */
    public <T> Optional<T> getAttribute(final AttributeGetter<Product, T> accessor);

    public default boolean hasAttribute(String attributeName) {
        return getAttributes().stream().anyMatch(attr -> attr.getName().equals(attributeName));
    }

    public default Optional<Attribute> getAttribute(final String attributeName) {
        requireNonNull(attributeName);
        return getAttributes().stream().filter(attr -> attr.getName().equals(attributeName)).findAny();
    }

    public List<Image> getImages();

    /**
     * The availability is set if the variant is tracked by the inventory. The field might not contain the latest inventory state (it is eventually consistent) and can be used as an optimization to reduce calls to the inventory service.
     *
     * @return availability
     */
    public Optional<ProductVariantAvailability> getAvailability();
}
