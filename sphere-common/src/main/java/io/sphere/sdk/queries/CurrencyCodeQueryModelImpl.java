package io.sphere.sdk.queries;

import javax.annotation.Nullable;

final class CurrencyCodeQueryModelImpl<T> extends QueryModelImpl<T> implements CurrencyCodeQueryModel<T> {
    public CurrencyCodeQueryModelImpl(@Nullable final QueryModel<T> parent, @Nullable final String pathSegment) {
        super(parent, pathSegment);
    }

    @Override
    public QueryPredicate<T> is(final String currencyCode) {
        return isPredicate(currencyCode);
    }
}
