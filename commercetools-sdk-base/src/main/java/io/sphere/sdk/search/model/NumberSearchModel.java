package io.sphere.sdk.search.model;

import javax.annotation.Nullable;
import java.math.BigDecimal;

public final class NumberSearchModel<T> extends RangeTermModelImpl<T, BigDecimal> {

    NumberSearchModel(@Nullable final SearchModel<T> parent, @Nullable final String pathSegment) {
        super(parent, pathSegment, TypeSerializer.ofNumber());
    }
}
