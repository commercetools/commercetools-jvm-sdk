package io.sphere.sdk.search;

import java.math.BigDecimal;
import java.util.Optional;

public class NumberSearchModel<T> extends RangeTermSearchModel<T, BigDecimal> {

    public NumberSearchModel(final Optional<? extends SearchModel<T>> parent, final String pathSegment) {
        super(parent, pathSegment);
    }

    public NumberSearchModel(final Optional<? extends SearchModel<T>> parent, final Optional<String> pathSegment) {
        super(parent, pathSegment);
    }

    @Override
    protected String render(final BigDecimal value) {
        return value.toPlainString();
    }
}
