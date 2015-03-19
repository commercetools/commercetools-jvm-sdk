package io.sphere.sdk.search;

import java.util.Optional;

public class MoneySearchModel<T, S extends SearchSortDirection> extends SearchModelImpl<T> {

    public MoneySearchModel(final Optional<? extends SearchModel<T>> parent, final String pathSegment) {
        super(parent, pathSegment);
    }

    public MoneyAmountSearchModel<T, S> amount() {
        return new MoneyAmountSearchModel<>(Optional.of(this), "centAmount");
    }

    public CurrencySearchModel<T, S> currency() {
        return new CurrencySearchModel<>(Optional.of(this), "currencyCode");
    }
}
