package io.sphere.sdk.queries;

public interface MoneyQueryModel<T> extends QueryModel<T> {
    LongQuerySortingModel<T> centAmount();

    CurrencyCodeQueryModel<T> currencyCode();
}
