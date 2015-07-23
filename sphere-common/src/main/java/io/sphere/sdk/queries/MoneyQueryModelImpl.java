package io.sphere.sdk.queries;

final class MoneyQueryModelImpl<T> extends QueryModelImpl<T>  implements MoneyQueryModel<T> {
    public MoneyQueryModelImpl(final QueryModel<T> parent, final String pathSegment) {
        super(parent, pathSegment);
    }

    @Override
    public LongQuerySortingModel<T> centAmount() {
        return longModel("centAmount");
    }

    @Override
    public CurrencyCodeQueryModel<T> currencyCode() {
        return currencyCodeModel("currencyCode");
    }
}
