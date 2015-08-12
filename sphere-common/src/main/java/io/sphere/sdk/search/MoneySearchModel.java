package io.sphere.sdk.search;

import javax.annotation.Nullable;

public class MoneySearchModel<T, S extends SearchSortDirection> extends SearchModelImpl<T> {

    public MoneySearchModel(@Nullable final SearchModel<T> parent, final String pathSegment) {
        super(parent, pathSegment);
    }

    public MoneyAmountSearchModel<T, S> centAmount() {
        return new MoneyAmountSearchModel<>(this, "centAmount");
    }

    public CurrencySearchModel<T, S> currency() {
        return new CurrencySearchModel<>(this, "currencyCode");
    }
}
