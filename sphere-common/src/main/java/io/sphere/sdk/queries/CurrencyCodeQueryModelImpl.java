package io.sphere.sdk.queries;

final class CurrencyCodeQueryModelImpl<T> extends QueryModelImpl<T> implements CurrencyCodeQueryModel<T> {
    public CurrencyCodeQueryModelImpl(final QueryModel<T> parent, final String pathSegment) {
        super(parent, pathSegment);
    }

    @Override
    public QueryPredicate<T> is(final String currencyCode) {
        return isPredicate(currencyCode);
    }
}
