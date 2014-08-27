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

    <T> Optional<T> getAttribute(final AttributeAccessor<Product, T> c);

    //TODO images

    //TODO availability
}
