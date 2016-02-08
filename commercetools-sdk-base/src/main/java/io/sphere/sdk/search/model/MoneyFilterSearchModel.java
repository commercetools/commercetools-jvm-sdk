package io.sphere.sdk.search.model;

import javax.annotation.Nullable;
import javax.money.CurrencyUnit;

public final class MoneyFilterSearchModel<T> extends SearchModelImpl<T> {

    MoneyFilterSearchModel(@Nullable final SearchModel<T> parent, @Nullable final String pathSegment) {
        super(parent, pathSegment);
    }

    public RangeTermFilterSearchModel<T, Long> centAmount() {
        return new MoneyCentAmountSearchModel<>(this, "centAmount").filtered();
    }

    public TermFilterSearchModel<T, CurrencyUnit> currency() {
        return new CurrencySearchModel<>(this, "currencyCode").filtered();
    }
}
