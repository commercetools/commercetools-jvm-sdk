package io.sphere.sdk.search.model;

import javax.annotation.Nullable;

public final class MoneyFacetedSearchSearchModel<T> extends SearchModelImpl<T> {

    MoneyFacetedSearchSearchModel(@Nullable final SearchModel<T> parent, @Nullable final String pathSegment) {
        super(parent, pathSegment);
    }

    public RangeTermFacetedSearchSearchModel<T> centAmount() {
        return new MoneyCentAmountSearchModel<>(this, "centAmount").facetedAndFiltered();
    }

    public TermFacetedSearchSearchModel<T> currency() {
        return new CurrencySearchModel<>(this, "currencyCode").facetedAndFiltered();
    }
}
