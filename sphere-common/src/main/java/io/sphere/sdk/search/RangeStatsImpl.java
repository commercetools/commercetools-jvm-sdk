package io.sphere.sdk.search;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.sphere.sdk.models.Base;
import org.apache.commons.lang3.StringUtils;

class RangeStatsImpl<T> extends Base implements RangeStats<T> {
    private final T lowerEndpoint;
    private final T upperEndpoint;
    private final Long count;
    private final T min;
    private final T max;
    private final T sum;
    private final Double mean;

    @JsonIgnore
    private RangeStatsImpl(final T lowerEndpoint, final T upperEndpoint, final Long count,
                           final T min, final T max, final T sum, final Double mean) {
        this.lowerEndpoint = lowerEndpoint;
        this.upperEndpoint = upperEndpoint;
        this.count = count;
        this.sum = sum;
        this.min = min;
        this.max = max;
        this.mean = mean;
    }

    RangeStatsImpl(final T from, final T to, final String fromStr, final String toStr, final Long count,
                   final long totalCount, final T min, final T max, final T total, final Double mean) {
        this(parseEndpoint(from, fromStr), parseEndpoint(to, toStr), count, min, max, total, mean);
    }


    @Override
    public T getLowerEndpoint() {
        return lowerEndpoint;
    }


    @Override
    public T getUpperEndpoint() {
        return upperEndpoint;
    }


    @Override
    public Long getCount() {
        return count;
    }


    @Override
    public T getSum() {
        return sum;
    }


    @Override
    public T getMin() {
        return min;
    }


    @Override
    public T getMax() {
        return max;
    }


    @Override
    public Double getMean() {
        return mean;
    }

    @JsonIgnore
    private static <T> T parseEndpoint(final T from, final String fromStr) {
        return StringUtils.isBlank(fromStr) ? null : from;
    }
}
