package io.sphere.sdk.queries;

import java.util.Optional;

final class MoneyQueryModelImpl<T> extends QueryModelImpl<T>  implements MoneyQueryModel<T> {
    public MoneyQueryModelImpl(final Optional<? extends QueryModel<T>> parent, final String pathSegment) {
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
