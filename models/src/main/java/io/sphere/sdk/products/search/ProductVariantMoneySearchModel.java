package io.sphere.sdk.products.search;

import io.sphere.sdk.products.ProductProjection;
import io.sphere.sdk.search.CurrencySearchModel;
import io.sphere.sdk.search.MoneyAmountSearchModel;
import io.sphere.sdk.search.SearchModel;
import io.sphere.sdk.search.SearchModelImpl;

import java.util.Optional;

public class ProductVariantMoneySearchModel extends SearchModelImpl<ProductProjection> {

    ProductVariantMoneySearchModel(final Optional<? extends SearchModel<ProductProjection>> parent, final Optional<String> pathSegment) {
        super(parent, pathSegment);
    }

    ProductVariantMoneySearchModel(Optional<? extends SearchModel<ProductProjection>> parent, final String pathSegment) {
        this(parent, Optional.of(pathSegment));
    }

    public MoneyAmountSearchModel<ProductProjection> amount() {
        return new MoneyAmountSearchModel<>(Optional.of(this), "centAmount");
    }

    public CurrencySearchModel<ProductProjection> currency() {
        return new CurrencySearchModel<>(Optional.of(this), "currencyCode");
    }
}