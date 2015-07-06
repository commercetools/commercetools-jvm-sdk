package io.sphere.sdk.queries;

import java.util.Optional;

final class CurrencyCodeQueryModelImpl<T> extends QueryModelImpl<T> implements CurrencyCodeQueryModel<T> {
    public CurrencyCodeQueryModelImpl(final Optional<? extends QueryModel<T>> parent, final String pathSegment) {
        super(parent, pathSegment);
    }

    @Override
    public QueryPredicate<T> is(final String currencyCode) {
        return isPredicate(currencyCode);
    }
}
