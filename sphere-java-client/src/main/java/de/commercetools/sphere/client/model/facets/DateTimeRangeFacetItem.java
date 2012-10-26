package de.commercetools.sphere.client.model.facets;

import org.joda.time.DateTime;
import com.google.common.base.Function;

/** Number of found resources for an individual range of a date range facet ({@link DateTimeRangeFacetResult}). */
public class DateTimeRangeFacetItem implements FacetResultItem {
    private DateTime from;
    private DateTime to;
    private int count;
    private DateTime mean;

    /** The lower endpoint of this range. */
    public DateTime getFrom() { return from; }

    /** The upper endpoint of this range. */
    public DateTime getTo() { return to; }

    /** Number of resources that fall into this range. */
    public int getCount() { return count; }

    /** Arithmetic mean of values that fall into this range. */
    public DateTime getMean() { return mean; }

    private DateTimeRangeFacetItem(DateTime from, DateTime to, int count, DateTime mean) {
        this.from = from;
        this.to = to;
        this.count = count;
        this.mean = mean;
    }

    /** Parses dates returned by the backend as milliseconds into joda.LocalDate instances. */
    static Function<RangeFacetItem, DateTimeRangeFacetItem> fromMilliseconds = new Function<RangeFacetItem, DateTimeRangeFacetItem>() {
        public DateTimeRangeFacetItem apply(RangeFacetItem rangeCount) {
            return new DateTimeRangeFacetItem(
                    new DateTime((long)rangeCount.getFrom()),
                    new DateTime((long)rangeCount.getTo()),
                    rangeCount.getCount(),
                    new DateTime((long)rangeCount.getMean()));
        }
    };
}