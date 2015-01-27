package io.sphere.sdk.products.search;

import io.sphere.sdk.products.ProductProjection;
import io.sphere.sdk.search.*;

import java.util.Optional;

public class ProductVariantPriceSearchModel extends SearchModelImpl<ProductProjection> {

    ProductVariantPriceSearchModel(final Optional<? extends SearchModel<ProductProjection>> parent, final Optional<String> pathSegment) {
        super(parent, pathSegment);
    }

    ProductVariantPriceSearchModel(Optional<? extends SearchModel<ProductProjection>> parent, final String pathSegment) {
        this(parent, Optional.of(pathSegment));
    }

    public MoneyAmountSearchModel<ProductProjection> amount() {
        return new MoneyAmountSearchModel<>(Optional.of(this), "centAmount");
    }

    public CurrencySearchModel<ProductProjection> currency() {
        return new CurrencySearchModel<>(Optional.of(this), "currencyCode");
    }
}