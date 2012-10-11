package de.commercetools.sphere.client.model;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

import java.util.Map;

/** Number of found resources for an individual range of a range facet ({@link RangeFacetResult}). */
@JsonIgnoreProperties({ "fromStr", "toStr" }) // 'fromStr' and 'toStr' are returned by the backend only for debugging purposes
public class RangeFacetItem implements FacetResultItem {
    private double from;
    private double to;
    private int count;
    private int totalCount;
    @JsonProperty("total")
    private double sum;
    private double mean;

    // for JSON deserializer
    private RangeFacetItem() {}

    /** The lower endpoint of this range. */
    public double getFrom() { return from; }

    /** The upper endpoint of this range. */
    public double getTo() { return to; }

    /** Number of resources that fall into this range. */
    public int getCount() { return count; }

    // Has as to do with multi-valued fields. Will be removed probably.
    public int getTotalCount() { return totalCount; }

    /** Sum of values that fall into this range. */
    public double getSum() { return sum; }

    /** Arithmetic mean of values that fall into this range. */
    public double getMean() { return mean; }
}