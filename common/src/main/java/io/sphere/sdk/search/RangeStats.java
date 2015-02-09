package io.sphere.sdk.search;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.sphere.sdk.models.Base;

import java.math.BigDecimal;
import java.util.Optional;

import static java.math.BigDecimal.*;

public class RangeStats extends Base {
    private final Optional<BigDecimal> lowerEndpoint;
    private final Optional<BigDecimal> upperEndpoint;
    private final long count;
    private final BigDecimal total;
    private final BigDecimal min;
    private final BigDecimal max;
    private final BigDecimal mean;

    @JsonIgnore
    private RangeStats(final Optional<BigDecimal> lowerEndpoint, final Optional<BigDecimal> upperEndpoint, final long count,
                       final long totalCount, final BigDecimal total, final BigDecimal min, final BigDecimal max, final BigDecimal mean) {
        this.lowerEndpoint = lowerEndpoint;
        this.upperEndpoint = upperEndpoint;
        this.count = count;
        this.total = total;
        this.min = min;
        this.max = max;
        this.mean = mean;
    }

    @JsonCreator
    RangeStats(final BigDecimal from, final BigDecimal to, final String fromStr, final String toStr, final long count,
                       final long totalCount, final BigDecimal total, final BigDecimal min, final BigDecimal max, final BigDecimal mean) {
        this(parseEndpoint(from, fromStr), parseEndpoint(to, toStr), count, totalCount, total, min, max, mean);
    }

    /**
     * Lower endpoint of the range.
     * @return the lower endpoint, or absent if no lower bound defined.
     */
    public Optional<BigDecimal> getLowerEndpoint() {
        return lowerEndpoint;
    }

    /**
     * Upper endpoint of the range.
     * @return the upper endpoint, or absent if no upper bound defined.
     */
    public Optional<BigDecimal> getUpperEndpoint() {
        return upperEndpoint;
    }

    /**
     * Number of results that fall into this range.
     * @return the amount of results in the range.
     */
    public long getCount() {
        return count;
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
    public static RangeStats of(final Optional<BigDecimal> from, final Optional<BigDecimal> to, final long count,
                                final BigDecimal sum, final BigDecimal min, final BigDecimal max, final BigDecimal mean) {
        return new RangeStats(from, to, count, count, sum, min, max, mean);
    }

    @JsonIgnore
    public static RangeStats of(final Optional<Double> from, final Optional<Double> to, final long count, final double sum,
                                final double min, final double max, final double mean) {
        return of(from.map(v -> valueOf(v)), to.map(v -> valueOf(v)), count, valueOf(sum), valueOf(min), valueOf(max), valueOf(mean));
    }

    @JsonIgnore
    private static Optional<BigDecimal> parseEndpoint(final BigDecimal endpoint, final String endpointAsString) {
        if (endpoint.doubleValue() == 0 && endpointAsString.isEmpty()) {
            return Optional.empty();
        } else {
            return Optional.of(endpoint);
        }
    }
}
