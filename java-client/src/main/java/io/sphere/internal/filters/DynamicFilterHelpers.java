package io.sphere.internal.filters;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Range;
import com.google.common.collect.Ranges;
import io.sphere.client.filters.FilterExpr;
import io.sphere.internal.facets.FacetExpressionBase;
import io.sphere.client.QueryParam;
import io.sphere.client.filters.expressions.FilterExpressions;
import io.sphere.client.filters.expressions.FilterType;
import net.jcip.annotations.Immutable;

import java.math.BigDecimal;
import java.util.List;

import static io.sphere.internal.util.ListUtil.list;
import static io.sphere.internal.util.SearchUtil.*;

/** Helper filter expressions.
 *
 * These expressions not only filter by a range, but also request that the results
 * include the min and max value across all returned products (by using a special facet).
 * This is useful e.g. for displaying correct slider bounds in an UI. */
public class DynamicFilterHelpers {

    /** The alias of a helper facet for a dynamic range filter. */
    public static String helperFacetAlias(String attribute) {
        return "dynamic_price_" + attribute;
    }

    // ------------------------
    // Helper facet
    // ------------------------

    /** Helper facet that makes search return min and max price across returned results . */
    @Immutable
    public static class HelperFacet extends FacetExpressionBase {
        public HelperFacet(String attribute) {
            super(attribute);
        }
        // use a big range of (1 to 1 billion) - hopefully all values fall into this range
        private final Range<Long> bigRange = Ranges.closed(0L, 1000*1000*1000L);

        @Override public List<QueryParam> createQueryParams() {
            String rangeString = longRangeToParam.apply(bigRange);
            return list(createRangeFacetParam(
                    attribute,
                    // use an alias so this facet doesn't conflict with other potential facets
                    // on variants.price.centAmount
                    rangeString + " as " + helperFacetAlias(attribute)));
        }
    }

    // ------------------------
    // Filter expressions
    // ------------------------

    /** Special filter expression that filters and also requests min and max values across returned results. */
    @Immutable
    public static class MoneyRangeFilterExpression extends FilterExpressionBase {
        private final com.google.common.collect.Range<BigDecimal> range;
        private final FilterType smartFilterType = FilterType.SMART;
        public MoneyRangeFilterExpression(String attribute, Range<BigDecimal> range) {
            super(attribute);
            this.range = range;
        }
        @Override public MoneyRangeFilterExpression setFilterType(FilterType filterType) {
           throw new IllegalStateException(
                   "MoneyRangeFilterExpression.filterType is always FilterType.SMART and cannot be changed.");
        }
        @Override public List<QueryParam> createQueryParams() {
            List<QueryParam> standardParams =
                    FilterExpr.moneyAttribute(attribute).range(range)
                        .setFilterType(smartFilterType)
                        .createQueryParams();
            List<QueryParam> helperFacetParams = new HelperFacet(attribute).createQueryParams();
            return ImmutableList.<QueryParam>builder().addAll(standardParams).addAll(helperFacetParams).build();
        }
    }

    /** Special filter expression that filters and also requests min and max values across returned results. */
    @Immutable
    public static class PriceRangeFilterExpression extends MoneyRangeFilterExpression {
        public static final String helperFacetAlias = helperFacetAlias(Names.priceFull);

        public PriceRangeFilterExpression(Range<BigDecimal> range) {
            super(Names.priceFull, range);
        }
        @Override public PriceRangeFilterExpression setFilterType(FilterType filterType) {
            this.filterType = filterType;
            return this;
        }
    }
}
