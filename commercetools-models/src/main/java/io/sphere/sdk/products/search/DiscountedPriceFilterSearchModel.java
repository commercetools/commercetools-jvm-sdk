package io.sphere.sdk.products.search;

import io.sphere.sdk.search.model.MoneyFilterSearchModel;
import io.sphere.sdk.search.model.ReferenceFilterSearchModel;
import io.sphere.sdk.search.model.SearchModel;
import io.sphere.sdk.search.model.SearchModelImpl;

import javax.annotation.Nullable;

public final class DiscountedPriceFilterSearchModel<T> extends SearchModelImpl<T> {

    DiscountedPriceFilterSearchModel(@Nullable final SearchModel<T> parent, @Nullable final String pathSegment) {
        super(parent, pathSegment);
    }

    public MoneyFilterSearchModel<T> value() {
        return moneyFilterSearchModel("value");
    }

    public ReferenceFilterSearchModel<T> discount() {
        return referenceFilterSearchModel("discount");
    }
}
