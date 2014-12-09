package io.sphere.sdk.search;

import org.javamoney.moneta.Money;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Optional;

public class MoneyAmountSearchModel<T> extends SearchModelImpl<T> implements RangeTermModel<T, Money>, SearchSortingModel<T> {

    public MoneyAmountSearchModel(final Optional<? extends SearchModel<T>> parent, final String pathSegment) {
        super(parent, pathSegment);
    }

    @Override
    public RangeTermFilterSearchModel<T, Money> filter() {
        return new RangeTermFilterSearchModel<>(Optional.of(this), Optional.empty(), this::render);
    }

    @Override
    public RangeTermFacetSearchModel<T, Money> facet() {
        return new RangeTermFacetSearchModel<>(Optional.of(this), Optional.empty(), this::render);
    }

    @Override
    public SearchSort<T> sort(SearchSortDirection sortDirection) {
        return new SphereSearchSort<>(this, sortDirection);
    }

    private String render(final Money value) {
        return toCents(value).toPlainString();
    }

    /**
     * Converts the given money amount to cent amount (e.g. from "20,00 EUR" to "2000").
     * @param money the amount to be converted.
     * @return the cent amount.
     */
    private static BigDecimal toCents(Money money) {
        return money.getNumberStripped().movePointRight(2).setScale(0, RoundingMode.HALF_EVEN);
    }
}
