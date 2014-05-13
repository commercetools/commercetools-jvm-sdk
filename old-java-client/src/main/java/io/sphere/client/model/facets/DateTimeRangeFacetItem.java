package io.sphere.client.model.facets;

import io.sphere.internal.util.SearchUtil;
import org.joda.time.DateTime;
import com.google.common.base.Function;

/** Number of resources found for an individual range of a date range facet ({@link DateTimeRangeFacetResult}). */
public class DateTimeRangeFacetItem implements FacetResultItem {
    private DateTime from;
    private DateTime to;
    private int count;
    private DateTime mean;

    /** The lower endpoint of this range. */
    public DateTime getFrom() { return from; }

    /** The upper endpoint of this range. */
    public DateTime getTo() { return to; }

    /** Arithmetic mean of values that fall into this range. */
    public DateTime getMean() { return mean; }

    /** Number of resources that fall into this range. */
    public int getCount() { return count; }

    /** Parses dates returned by the backend as milliseconds into joda.DateTime instances. */
    static Function<RangeFacetItem, DateTimeRangeFacetItem> fromMilliseconds = new Function<RangeFacetItem, DateTimeRangeFacetItem>() {
        public DateTimeRangeFacetItem apply(RangeFacetItem rangeCount) {
            return new DateTimeRangeFacetItem(
                    new DateTime((long)rangeCount.getFrom()),
                    SearchUtil.adjustDateTimeBackFromSearch(new DateTime((long)rangeCount.getTo())),
                    new DateTime((long)rangeCount.getMean()),
                    rangeCount.getCount());
        }
    };

    private DateTimeRangeFacetItem(DateTime from, DateTime to, DateTime mean, int count) {
        this.from = from;
        this.to = to;
        this.mean = mean;
        this.count = count;
    }

    @Override
    public String toString() {
        return "DateTimeRangeFacetItem{" +
                "from=" + from +
                ", to=" + to +
                ", count=" + count +
                ", mean=" + mean +
                '}';
    }
}
