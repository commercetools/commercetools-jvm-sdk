package io.sphere.sdk.products.expansion;

import io.sphere.sdk.queries.ExpansionModel;

import java.util.Optional;

public class ProductVariantExpansionModel<T> extends ExpansionModel<T> {
    ProductVariantExpansionModel(final Optional<String> parentPath, final String path) {
        super(parentPath, Optional.of(path));
    }

    public PriceExpansionModel<T> prices() {
        return prices("*");
    }

    public PriceExpansionModel<T> prices(final int index) {
        return prices("" + index);
    }

    private PriceExpansionModel<T> prices(final String index) {
        return new PriceExpansionModel<>(pathExpressionOption(), "prices[" + index + "]");
    }
}

