package io.sphere.sdk.search.model;

import javax.annotation.Nullable;
import javax.money.CurrencyUnit;

public final class MoneyFacetSearchModel<T> extends SearchModelImpl<T> {

    MoneyFacetSearchModel(@Nullable final SearchModel<T> parent, @Nullable final String pathSegment) {
        super(parent, pathSegment);
    }

    public RangeTermFacetSearchModel<T, Long> centAmount() {
        return new MoneyCentAmountSearchModel<>(this, "centAmount").faceted();
    }

    public TermFacetSearchModel<T, CurrencyUnit> currency() {
        return new CurrencySearchModel<>(this, "currencyCode").faceted();
    }
}
