package io.sphere.sdk.search.model;

import io.sphere.sdk.models.Base;

import javax.annotation.Nullable;

public final class SimpleRangeStats extends Base {
    @Nullable
    private final String lowerEndpoint;
    @Nullable
    private final String upperEndpoint;
    private final Long count;
    private final String min;
    private final String max;

    private SimpleRangeStats(@Nullable final String lowerEndpoint, @Nullable final String upperEndpoint, final Long count,
                             final String min, final String max) {
        this.lowerEndpoint = lowerEndpoint;
        this.upperEndpoint = upperEndpoint;
        this.count = count;
        this.min = min;
        this.max = max;
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

    public static SimpleRangeStats of(@Nullable final String lowerEndpoint, @Nullable final String upperEndpoint,
                                      final Long count, final String min, final String max) {
        return new SimpleRangeStats(lowerEndpoint, upperEndpoint, count, min, max);
    }
}
