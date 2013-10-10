package io.sphere.client.model.facets;

import com.google.common.base.Function;
import io.sphere.internal.util.SearchUtil;

/** Number of resources found for an individual range of a number range facet ({@link NumberRangeFacetResult}). */
public class NumberRangeFacetItem implements FacetResultItem {
    private Double from;
    private Double to;
    private Double mean;
    private int count;

    /** The lower endpoint of this range. */
    public Double getFrom() { return from; }

    /** The upper endpoint of this range. */
    public Double getTo() { return to; }

    /** Arithmetic mean of values that fall into this range. */
    public Double getMean() { return mean; }

    /** Number of resources that fall into this range. */
    public int getCount() { return count; }

    static Function<RangeFacetItem, NumberRangeFacetItem> fromBackendDoubles = new Function<RangeFacetItem, NumberRangeFacetItem>() {
        public NumberRangeFacetItem apply(RangeFacetItem rangeCount) {
            return new NumberRangeFacetItem(
                    rangeCount.getFrom(),
                    SearchUtil.adjustDoubleBackFromSearch(rangeCount.getTo()),
                    rangeCount.getMean(),
                    rangeCount.getCount());
        }
    };

    private NumberRangeFacetItem(double from, double to, double mean, int count) {
        this.from = from;
        this.to = to;
        this.mean = mean;
        this.count = count;
    }

    @Override
    public String toString() {
        return "NumberRangeFacetItem{" +
                "from=" + from +
                ", to=" + to +
                ", mean=" + mean +
                ", count=" + count +
                '}';
    }
}
