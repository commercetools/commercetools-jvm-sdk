package io.sphere.sdk.search;

import javax.annotation.Nullable;
import javax.money.CurrencyUnit;

public class CurrencySearchModel<T, S extends SearchSortDirection> extends TermModelImpl<T, CurrencyUnit> implements SearchSortingModel<T, S> {

    public CurrencySearchModel(@Nullable final SearchModel<T> parent, final String pathSegment) {
        super(parent, pathSegment);
    }

    @Override
    public FilterSearchModel<T, CurrencyUnit> filtered() {
        return new FilterSearchModel<>(this, null, TypeSerializer.ofCurrency());
    }

    @Override
    public FacetSearchModel<T, CurrencyUnit> faceted() {
        return new FacetSearchModel<>(this, null, TypeSerializer.ofCurrency());
    }

    @Override
    public SearchSort<T> sorted(S sortDirection) {
        return new SphereSearchSort<>(this, sortDirection);
    }
}