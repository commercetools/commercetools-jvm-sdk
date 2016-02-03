package io.sphere.sdk.search.model;

import javax.annotation.Nullable;

public final class MoneyCentAmountSearchModel<T> extends RangeTermModelImpl<T, Long> {

    public MoneyCentAmountSearchModel(@Nullable final SearchModel<T> parent, @Nullable final String pathSegment) {
        super(parent, pathSegment, TypeSerializer.ofMoneyCentAmount());
    }
}
