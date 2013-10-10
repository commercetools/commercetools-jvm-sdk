package io.sphere.client.model.facets;

import com.google.common.base.Function;
import io.sphere.internal.util.SearchUtil;
import io.sphere.client.model.Money;

import java.math.BigDecimal;

/** Number of resources found for an individual range of a money range facet ({@link MoneyRangeFacetResult}). */
public class MoneyRangeFacetItem implements FacetResultItem {
    private BigDecimal from;
    private BigDecimal to;
    private BigDecimal mean;
    private int count;

    /** The lower endpoint of this range. */
    public BigDecimal getFrom() { return from; }

    /** The upper endpoint of this range. */
    public BigDecimal getTo() { return to; }

    /** Arithmetic mean of values that fall into this range. */
    public BigDecimal getMean() { return mean; }

    /** Number of resources that fall into this range. */
    public int getCount() { return count; }

    /** Parses cent amounts returned by the backend into BigDecimals representing money amounts. */
    static Function<RangeFacetItem, MoneyRangeFacetItem> fromCents = new Function<RangeFacetItem, MoneyRangeFacetItem>() {
        public MoneyRangeFacetItem apply(RangeFacetItem rangeCount) {
            return new MoneyRangeFacetItem(
                    Money.centsToAmount(rangeCount.getFrom()),
                    Money.centsToAmount(SearchUtil.adjustLongBackFromSearch((long)rangeCount.getTo())),
                    Money.centsToAmount(rangeCount.getMean()),
                    rangeCount.getCount());
        }
    };

    private MoneyRangeFacetItem(BigDecimal from, BigDecimal to, BigDecimal mean, int count) {
        this.from = from;
        this.to = to;
        this.mean = mean;
        this.count = count;
    }

    @Override
    public String toString() {
        return "MoneyRangeFacetItem{" +
                "from=" + from +
                ", to=" + to +
                ", mean=" + mean +
                ", count=" + count +
                '}';
    }
}
