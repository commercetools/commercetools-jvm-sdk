package io.sphere.sdk.products;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.sphere.sdk.models.DefaultModel;
import io.sphere.sdk.models.Reference;
import io.sphere.sdk.producttypes.ProductType;

@JsonDeserialize(as=ProductImpl.class)
public interface Product extends DefaultModel {
    Reference<ProductType> getProductType();
}
