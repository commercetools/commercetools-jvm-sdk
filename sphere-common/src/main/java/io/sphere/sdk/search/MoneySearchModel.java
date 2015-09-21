package io.sphere.sdk.search;

import javax.annotation.Nullable;

public class MoneySearchModel<T, S extends DirectionlessSearchSortModel<T>> extends SortableSearchModel<T, S> {

    public MoneySearchModel(@Nullable final SearchModel<T> parent, @Nullable final String pathSegment, final SortBuilder<T, S> sortBuilder) {
        super(parent, pathSegment, sortBuilder);
    }

    public MoneyCentAmountSearchModel<T, S> centAmount() {
        return new MoneyCentAmountSearchModel<>(this, "centAmount", sortBuilder);
    }

    public CurrencySearchModel<T, S> currency() {
        return new CurrencySearchModel<>(this, "currencyCode", sortBuilder);
    }
}
