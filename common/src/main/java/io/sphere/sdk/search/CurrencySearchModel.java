package io.sphere.sdk.search;

import java.util.Currency;
import java.util.Optional;

public class CurrencySearchModel<T> extends SearchModelImpl<T> implements TermModel<T, Currency>, SearchSortingModel<T> {

    public CurrencySearchModel(final Optional<? extends SearchModel<T>> parent, final String pathSegment) {
        super(parent, pathSegment);
    }

    @Override
    public TermFilterSearchModel<T, Currency> filter() {
        return new TermFilterSearchModel<>(Optional.of(this), Optional.empty(), this::render);
    }

    @Override
    public TermFacetSearchModel<T, Currency> facet() {
        return new TermFacetSearchModel<>(Optional.of(this), Optional.empty(), this::render);
    }

    @Override
    public SearchSort<T> sort(SearchSortDirection sortDirection) {
        return new SphereSearchSort<>(this, sortDirection);
    }

    private String render(final Currency value) {
        return "\"" + currencyToString(value) + "\"";
    }

    /**
     * Converts the given currency to string format.
     * @param currency the currency instance.
     * @return the currency as string.
     */
    private static String currencyToString(final Currency currency) {
        return currency.getCurrencyCode().toLowerCase();
    }
}