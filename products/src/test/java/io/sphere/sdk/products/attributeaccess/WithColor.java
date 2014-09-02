package io.sphere.sdk.products.attributeaccess;

import io.sphere.sdk.models.AttributeAccessor;
import io.sphere.sdk.models.AttributeMapper;
import io.sphere.sdk.models.TypeReferences;
import io.sphere.sdk.products.Product;

public interface WithColor {
    default AttributeAccessor<Product, String> hexColor() {
        return AttributeAccessor.ofString("hex-color");
    }

    default AttributeAccessor<Product, Double> rgb() {
        return AttributeAccessor.of("rgb", AttributeMapper.of(TypeReferences.doubleTypeReference()));
    }
}
