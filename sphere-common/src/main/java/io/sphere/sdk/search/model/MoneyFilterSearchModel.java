package io.sphere.sdk.search.model;

import javax.annotation.Nullable;
import javax.money.CurrencyUnit;

public class MoneyFilterSearchModel<T> extends SearchModelImpl<T> {

    public MoneyFilterSearchModel(@Nullable final SearchModel<T> parent, @Nullable final String pathSegment) {
        super(parent, pathSegment);
    }

    public RangeFilterSearchModel<T, Long> centAmount() {
        return new MoneyCentAmountSearchModel<>(this, "centAmount").filtered();
    }

    public TermFilterSearchModel<T, CurrencyUnit> currency() {
        return new CurrencySearchModel<>(this, "currencyCode").filtered();
    }
}
