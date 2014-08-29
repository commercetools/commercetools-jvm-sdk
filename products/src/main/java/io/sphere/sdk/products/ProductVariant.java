package io.sphere.sdk.products;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.sphere.sdk.models.AttributeAccessor;

import java.util.List;
import java.util.Optional;

@JsonDeserialize(as = ProductVariantImpl.class)
public interface ProductVariant {

    long getId();

    Optional<String> getSku();

    List<Price> getPrices();

    List<Attribute> getAttributes();

    /**
     * Access one attribute of a specific name and type which is known in the first place, consult {@link io.sphere.sdk.models.AttributeAccessor} how to implement these.
     *
     * @throws io.sphere.sdk.models.exceptions.AttributeMappingException if the type of attribute cannot be parsed
     *
     * @param accessor declaration of the name and type of the attribute
     * @param <T> the underlying type of the attribute
     * @return the value of the attribute, or Optional.empty if absent
     */
    <T> Optional<T> getAttribute(final AttributeAccessor<Product, T> accessor);

    //TODO images

    //TODO availability
}
