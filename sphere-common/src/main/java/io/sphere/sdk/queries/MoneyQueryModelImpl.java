package io.sphere.sdk.queries;

import javax.annotation.Nullable;

final class MoneyQueryModelImpl<T> extends QueryModelImpl<T>  implements MoneyQueryModel<T> {
    public MoneyQueryModelImpl(@Nullable final QueryModel<T> parent, @Nullable final String pathSegment) {
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
