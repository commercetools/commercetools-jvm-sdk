package io.sphere.sdk.products.expansion;

import io.sphere.sdk.expansion.ExpansionModel;

import javax.annotation.Nullable;
import java.util.List;

public class ProductVariantExpansionModel<T> extends ExpansionModel<T> {
    ProductVariantExpansionModel(@Nullable final String parentPath, @Nullable final String path) {
        super(parentPath, path);
    }

    ProductVariantExpansionModel(@Nullable final String parentPath, final List<String> paths) {
        super(parentPath, paths);
    }

    public PriceExpansionModel<T> prices() {
        return prices("*");
    }

    public PriceExpansionModel<T> prices(final int index) {
        return prices("" + index);
    }

    public ProductAttributeExpansionModel<T> attributes() {
        return new ProductAttributeExpansionModel<>(pathExpression(), "attributes[*]");
    }

    private PriceExpansionModel<T> prices(final String index) {
        return new PriceExpansionModel<>(pathExpression(), "prices[" + index + "]");
    }
}

