package io.sphere.sdk.products.attributes;

import io.sphere.sdk.models.Referenceable;
import io.sphere.sdk.producttypes.ProductType;

import javax.annotation.Nullable;

public interface ProductAttributeConverter<T> {
    @Nullable
    T convert(final Attribute attribute, final Referenceable<ProductType> productType);
}
