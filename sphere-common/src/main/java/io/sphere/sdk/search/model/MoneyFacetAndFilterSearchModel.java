package io.sphere.sdk.search.model;

import javax.annotation.Nullable;
import javax.money.CurrencyUnit;

public class MoneyFacetAndFilterSearchModel<T> extends SearchModelImpl<T> {

    MoneyFacetAndFilterSearchModel(@Nullable final SearchModel<T> parent, @Nullable final String pathSegment) {
        super(parent, pathSegment);
    }

    public RangeFacetAndFilterSearchModel<T, Long> centAmount() {
        return new MoneyCentAmountSearchModel<>(this, "centAmount").facetedAndFiltered();
    }

    public TermFacetAndFilterSearchModel<T, CurrencyUnit> currency() {
        return new CurrencySearchModel<>(this, "currencyCode").facetedAndFiltered();
    }
}
