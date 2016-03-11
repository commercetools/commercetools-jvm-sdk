package io.sphere.sdk.products.search;

import io.sphere.sdk.search.model.*;

import javax.annotation.Nullable;

public final class ScopedPriceFilterSearchModel<T> extends SearchModelImpl<T> {

    ScopedPriceFilterSearchModel(@Nullable final SearchModel<T> parent, @Nullable final String pathSegment) {
        super(parent, pathSegment);
    }

    public MoneyFilterSearchModel<T> value() {
        return moneyFilterSearchModel("value");
    }

    public DiscountedPriceFilterSearchModel<T> discounted() {
        return new DiscountedPriceFilterSearchModel<>(this, "discounted");
    }

    public MoneyFilterSearchModel<T> currentValue() {
        return moneyFilterSearchModel("currentValue");
    }
}
