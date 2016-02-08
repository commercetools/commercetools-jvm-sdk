package io.sphere.sdk.search.model;

import javax.annotation.Nullable;
import javax.money.CurrencyUnit;

public final class CurrencySearchModel<T> extends TermModelImpl<T, CurrencyUnit> {

    public CurrencySearchModel(@Nullable final SearchModel<T> parent, @Nullable final String pathSegment) {
        super(parent, pathSegment, TypeSerializer.ofCurrency());
    }
}