package io.sphere.sdk.products.expansion;

public interface ProductVariantExpansionModel<T> {
    PriceExpansionModel<T> prices();

    PriceExpansionModel<T> prices(int index);

    ProductAttributeExpansionModel<T> attributes();
}
