package io.sphere.client.model.facets;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

/** Number of found resources for an individual range of a range facet ({@link RangeFacetResultRaw}). */
@JsonIgnoreProperties({ "fromStr", "toStr" }) // 'fromStr' and 'toStr' are returned by the backend only for debugging purposes
public class RangeFacetItem implements FacetResultItem {
    private double from;
    private double to;
    private int count;
    private int totalCount;
    @JsonProperty("total") private double sum;
    private double min;
    private double max;
    private double mean;

    // for JSON deserializer
    private RangeFacetItem() {}

    /** The lower endpoint of this range. */
    public double getFrom() { return from; }

    /** The upper endpoint of this range. */
    public double getTo() { return to; }

    /** Number of results that fall into this range. */
    public int getCount() { return count; }

    // Has as to do with multi-valued fields. Will be removed probably.
    public int getTotalCount() { return totalCount; }

    /** Sum of values that fall into this range, or 0 if none. */
    public double getSum() { return sum; }

    /** Minimum value that falls into this range, or 0 if none. */
    public double getMin() { return min; }

    /** Maximum value that falls into this range, or 0 if none. */
    public double getMax() { return max; }

    /** Arithmetic mean of values that fall into this range, or 0 if none. */
    public double getMean() { return mean; }

    @Override
    public String toString() {
        return "RangeFacetItem{" +
                "from=" + from +
                ", to=" + to +
                ", count=" + count +
                ", totalCount=" + totalCount +
                ", sum=" + sum +
                ", min=" + min +
                ", max=" + max +
                ", mean=" + mean +
                '}';
    }
}
