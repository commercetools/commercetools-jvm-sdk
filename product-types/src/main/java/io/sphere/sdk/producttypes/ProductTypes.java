package io.sphere.sdk.producttypes;

public final class ProductTypes {

    private ProductTypes() {
        //pure utility class
    }

    public static ProductTypeQuery query() {
        return new ProductTypeQuery();
    }
}
