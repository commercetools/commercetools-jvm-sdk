package io.sphere.sdk.search.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.sphere.sdk.models.Base;
import org.apache.commons.lang3.StringUtils;

import javax.annotation.Nullable;

public final class RangeStats extends Base {
    @Nullable
    private final String lowerEndpoint;
    @Nullable
    private final String upperEndpoint;
    private final Long count;
    private final String min;
    private final String max;
    private final String sum;
    private final Double mean;

    @JsonIgnore
    private RangeStats(@Nullable final String lowerEndpoint, @Nullable final String upperEndpoint, final Long count,
                       final String min, final String max, final String sum, final Double mean) {
        this.lowerEndpoint = lowerEndpoint;
        this.upperEndpoint = upperEndpoint;
        this.count = count;
        this.sum = sum;
        this.min = min;
        this.max = max;
        this.mean = mean;
    }

    @JsonCreator
    RangeStats(final String from, final String to, final String fromStr, final String toStr, final Long count,
               final Long totalCount, final String min, final String max, final String total, final Double mean) {
        this(parseEndpoint(from, fromStr), parseEndpoint(to, toStr), count, min, max, total, mean);
    }

    /**
     * Lower endpoint of the range.
     * @return the lower endpoint, or absent if no lower bound defined.
     */
    @Nullable
    public String getLowerEndpoint() {
        return lowerEndpoint;
    }

    /**
     * Upper endpoint of the range.
     * @return the upper endpoint, or absent if no upper bound defined.
     */
    @Nullable
    public String getUpperEndpoint() {
        return upperEndpoint;
    }

    /**
     * Number of results that fall into this range.
     * @return the amount of results in the range.
     */
    public Long getCount() {
        return count;
    }

    /**
     * Sum of the values contained within the range.
     * @return the sum of the values.
     */
    public String getSum() {
        return sum;
    }

    /**
     * Minimum value contained within the range.
     * @return the minimum value.
     */
    public String getMin() {
        return min;
    }

    /**
     * Maximum value contained within the range.
     * @return the maximum value.
     */
    public String getMax() {
        return max;
    }

    /**
     * Arithmetic mean of the values contained within the range.
     * @return the mean of the values.
     */
    public Double getMean() {
        return mean;
    }

    @JsonIgnore
    public static RangeStats of(@Nullable final String lowerEndpoint, @Nullable final String upperEndpoint, final Long count,
                                final String min, final String max, final String sum, final Double mean) {
        return new RangeStats(lowerEndpoint, upperEndpoint, count, min, max, sum, mean);
    }

    @JsonIgnore
    private static String parseEndpoint(final String from, final String fromStr) {
        return StringUtils.isBlank(fromStr) ? null : from;
    }
}
