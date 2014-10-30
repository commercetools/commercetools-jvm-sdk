package io.sphere.sdk.search;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.sphere.sdk.models.Base;

import java.math.BigDecimal;

public class RangeStats extends Base {
    private final BigDecimal from;
    private final BigDecimal to;
    private final long count;
    private final long totalCount;
    private final BigDecimal total;
    private final BigDecimal min;
    private final BigDecimal max;
    private final BigDecimal mean;

    private RangeStats(final BigDecimal from, final BigDecimal to, final long count, final long totalCount,
                       final BigDecimal total, final BigDecimal min, final BigDecimal max, final BigDecimal mean) {
        this.from = from;
        this.to = to;
        this.count = count;
        this.totalCount = totalCount;
        this.total = total;
        this.min = min;
        this.max = max;
        this.mean = mean;
    }

    /**
     * Lower endpoint of the range.
     * @return the lower endpoint.
     */
    public BigDecimal getFrom() {
        return from;
    }

    /**
     * Upper endpoint of the range.
     * @return the upper endpoint.
     */
    public BigDecimal getTo() {
        return to;
    }

    /**
     * Number of results that fall into this range.
     * @return the amount of results in the range.
     */
    public long getCount() {
        return count;
    }

    // TODO check documentation
    public long getTotalCount() {
        return totalCount;
    }

    /**
     * Sum of the values contained within the range.
     * @return the sum of the values.
     */
    public BigDecimal getSum() {
        return total;
    }

    /**
     * Minimum value contained within the range.
     * @return the minimum value.
     */
    public BigDecimal getMin() {
        return min;
    }

    /**
     * Maximum value contained within the range.
     * @return the maximum value.
     */
    public BigDecimal getMax() {
        return max;
    }

    /**
     * Arithmetic mean of the values contained within the range.
     * @return the mean of the values.
     */
    public BigDecimal getMean() {
        return mean;
    }

    @JsonIgnore
    public static RangeStats of(final BigDecimal from, final BigDecimal to, final long count, final long totalCount,
                                final BigDecimal sum, final BigDecimal min, final BigDecimal max, final BigDecimal mean) {
        return new RangeStats(from, to, count, totalCount, sum, min, max, mean);
    }

    @JsonIgnore
    public static RangeStats of(final int from, final int to, final long count, final long totalCount,
                                final int sum, final int min, final int max, final double mean) {
        return of(BigDecimal.valueOf(from), BigDecimal.valueOf(to), count, totalCount, BigDecimal.valueOf(sum),
                BigDecimal.valueOf(min), BigDecimal.valueOf(max), BigDecimal.valueOf(mean));
    }
}
