package de.commercetools.sphere.client.model;

import org.joda.time.LocalDate;
import com.google.common.base.Function;

/** Number of found resources for an individual range of a date range facet ({@link DateRangeFacetResult}). */
public class DateRangeCount {
    private LocalDate from;
    private LocalDate to;
    private int count;
    private LocalDate mean;

    /** The lower endpoint of this range. */
    public LocalDate getFrom() { return from; }

    /** The upper endpoint of this range. */
    public LocalDate getTo() { return to; }

    /** Number of resources that fall into this range. */
    public int getCount() { return count; }

    /** Arithmetic mean of values that fall into this range. */
    public LocalDate getMean() { return mean; }

    private DateRangeCount(LocalDate from, LocalDate to, int count, LocalDate mean) {
        this.from = from;
        this.to = to;
        this.count = count;
        this.mean = mean;
    }

    /** Parses dates returned by the backend as milliseconds into joda.LocalDate instances. */
    static Function<RangeCount, DateRangeCount> fromMilliseconds = new Function<RangeCount, DateRangeCount>() {
        public DateRangeCount apply(RangeCount rangeCount) {
            return new DateRangeCount(
                    new LocalDate((long)rangeCount.getFrom()),
                    new LocalDate((long)rangeCount.getTo()),
                    rangeCount.getCount(),
                    new LocalDate((long)rangeCount.getMean()));
        }
    };
}