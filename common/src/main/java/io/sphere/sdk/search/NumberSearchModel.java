package io.sphere.sdk.search;

import java.math.BigDecimal;
import java.util.Optional;

public class NumberSearchModel<T> extends SearchModelImpl<T> {

    public NumberSearchModel(final Optional<? extends SearchModel<T>> parent, final String pathSegment) {
        super(parent, pathSegment);
    }

    public RangeTermFilterSearchModel<T, BigDecimal> filter() {
        return new RangeTermFilterSearchModel<>(Optional.of(this), Optional.empty(), this::render);
    }

    public RangeTermFacetSearchModel<T, BigDecimal> facet() {
        return new RangeTermFacetSearchModel<>(Optional.of(this), Optional.empty(), this::render);
    }

    private String render(final BigDecimal value) {
        return value.toPlainString();
    }
}
