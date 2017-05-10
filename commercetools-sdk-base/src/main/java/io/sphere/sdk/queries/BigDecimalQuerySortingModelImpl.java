package io.sphere.sdk.queries;

import javax.annotation.Nullable;
import java.math.BigDecimal;

final class BigDecimalQuerySortingModelImpl<T> extends NumberLikeQuerySortingModelImpl<T, BigDecimal> implements BigDecimalQuerySortingModel<T> {
    public BigDecimalQuerySortingModelImpl(@Nullable final QueryModel<T> parent, @Nullable final String pathSegment) {
        super(parent, pathSegment);
    }
}
