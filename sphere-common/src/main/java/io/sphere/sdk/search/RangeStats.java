package io.sphere.sdk.search;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import javax.annotation.Nullable;

@JsonDeserialize(as = BigDecimalRangeStats.class)
public interface RangeStats<T> {
    /**
     * Lower endpoint of the range.
     * @return the lower endpoint, or absent if no lower bound defined.
     */
    @Nullable
    T getLowerEndpoint();

    /**
     * Upper endpoint of the range.
     * @return the upper endpoint, or absent if no upper bound defined.
     */
    @Nullable
    T getUpperEndpoint();

    /**
     * Number of results that fall into this range.
     * @return the amount of results in the range.
     */
    long getCount();

    /**
     * Sum of the values contained within the range.
     * @return the sum of the values.
     */
    T getSum();

    /**
     * Minimum value contained within the range.
     * @return the minimum value.
     */
    T getMin();

    /**
     * Maximum value contained within the range.
     * @return the maximum value.
     */
    T getMax();

    /**
     * Arithmetic mean of the values contained within the range.
     * @return the mean of the values.
     */
    double getMean();
}
