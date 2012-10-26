package de.commercetools.sphere.client.model.facets;

import org.joda.time.LocalTime;
import com.google.common.base.Function;

/** Number of found resources for an individual range of a date range facet ({@link TimeRangeFacetResult}). */
public class TimeRangeFacetItem implements FacetResultItem {
    private LocalTime from;
    private LocalTime to;
    private int count;
    private LocalTime mean;

    /** The lower endpoint of this range. */
    public LocalTime getFrom() { return from; }

    /** The upper endpoint of this range. */
    public LocalTime getTo() { return to; }

    /** Number of resources that fall into this range. */
    public int getCount() { return count; }

    /** Arithmetic mean of values that fall into this range. */
    public LocalTime getMean() { return mean; }

    private TimeRangeFacetItem(LocalTime from, LocalTime to, int count, LocalTime mean) {
        this.from = from;
        this.to = to;
        this.count = count;
        this.mean = mean;
    }

    /** Parses times returned by the backend as milliseconds into joda.LocalTime instances. */
    static Function<RangeFacetItem, TimeRangeFacetItem> fromMilliseconds = new Function<RangeFacetItem, TimeRangeFacetItem>() {
        public TimeRangeFacetItem apply(RangeFacetItem rangeCount) {
            return new TimeRangeFacetItem(
                    new LocalTime((long)rangeCount.getFrom()),
                    new LocalTime((long)rangeCount.getTo()),
                    rangeCount.getCount(),
                    new LocalTime((long)rangeCount.getMean()));
        }
    };
}