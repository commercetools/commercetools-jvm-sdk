package io.sphere.sdk.search;

import javax.annotation.Nullable;
import javax.money.CurrencyUnit;
import java.util.Optional;

public class CurrencySearchModel<T, S extends SearchSortDirection> extends SearchModelImpl<T> implements TermModel<T, CurrencyUnit>, SearchSortingModel<T, S> {

    public CurrencySearchModel(@Nullable final SearchModel<T> parent, final String pathSegment) {
        super(parent, pathSegment);
    }

    @Override
    public FilterSearchModel<T, CurrencyUnit> filterBy() {
        return new FilterSearchModel<>(this, null, TypeSerializer.ofCurrency());
    }

    @Override
    public FacetSearchModel<T, CurrencyUnit> facetOf() {
        return new FacetSearchModel<>(this, null, TypeSerializer.ofCurrency());
    }

    @Override
    public SearchSort<T> sort(S sortDirection) {
        return new SphereSearchSort<>(this, sortDirection);
    }
}