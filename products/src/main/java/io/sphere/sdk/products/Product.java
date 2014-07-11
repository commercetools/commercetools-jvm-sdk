package io.sphere.sdk.products;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.sphere.sdk.models.DefaultModel;
import io.sphere.sdk.models.Reference;
import io.sphere.sdk.producttypes.ProductType;

@JsonDeserialize(as=ProductImpl.class)
public interface Product extends DefaultModel {
    Reference<ProductType> getProductType();

    public static TypeReference<Product> typeReference(){
        return new TypeReference<Product>() {
            @Override
            public String toString() {
                return "TypeReference<Product>";
            }
        };
    }

    public static ProductQuery query() {
        return new ProductQuery();
    }
}
