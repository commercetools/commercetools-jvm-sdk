package io.sphere.sdk.search;

import com.fasterxml.jackson.annotation.JsonCreator;

import java.math.BigDecimal;

final class BigDecimalRangeStats extends RangeStatsImpl<BigDecimal> {
    @JsonCreator
    public BigDecimalRangeStats(final BigDecimal from, final BigDecimal to, final String fromStr, final String toStr, final long count, final long totalCount, final BigDecimal min, final BigDecimal max, final BigDecimal total, final double mean) {
        super(from, to, fromStr, toStr, count, totalCount, min, max, total, mean);
    }
}
