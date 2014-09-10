package io.sphere.sdk.products;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import java.util.List;
import java.util.Optional;

import static java.util.Objects.requireNonNull;

@JsonDeserialize(as = ProductVariantImpl.class)
public interface ProductVariant {

    long getId();

    Optional<String> getSku();

    List<Price> getPrices();

    List<Attribute> getAttributes();

    /**
     * Access one attribute of a specific name and type which is known in the first place, consult {@link AttributeAccessor} how to implement these.
     *
     * @throws io.sphere.sdk.products.exceptions.AttributeMappingException if the type of attribute cannot be parsed
     *
     * @param accessor declaration of the name and type of the attribute
     * @param <T> the underlying type of the attribute
     * @return the value of the attribute, or Optional.empty if absent
     */
    <T> Optional<T> getAttribute(final AttributeAccessor<Product, T> accessor);

    default boolean hasAttribute(String attributeName) {
        return getAttributes().stream().anyMatch(attr -> attr.getName().equals(attributeName));
    }

    default Optional<Attribute> getAttribute(final String attributeName) {
        requireNonNull(attributeName);
        return getAttributes().stream().filter(attr -> attr.getName().equals(attributeName)).findAny();
    }

    //TODO images

    //TODO availability
}
