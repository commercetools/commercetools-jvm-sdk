package io.sphere.internal.util;

import com.google.common.base.Function;
import com.google.common.base.Joiner;
import com.google.common.base.Predicate;
import com.google.common.base.Strings;
import com.google.common.collect.FluentIterable;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Range;
import com.google.common.collect.Ranges;
import io.sphere.client.ProductSort;
import io.sphere.client.filters.expressions.FilterType;
import io.sphere.client.QueryParam;
import io.sphere.client.model.Money;
import io.sphere.client.shop.model.Category;
import static io.sphere.internal.util.ListUtil.*;

import io.sphere.client.shop.model.Variant;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.Duration;
import org.joda.time.format.ISODateTimeFormat;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

// date     yyyy-MM-dd                  ISODateTimeFormat.date().print(ld)
// time     HH:mm:ss.SSS                ISODateTimeFormat.time().print(lt)
// datetime yyyy-MM-ddTHH:mm:ss.SSSZZ   ISODateTimeFormat.dateTime().print(dt.withZone(DateTimeZone.UTC))

/** Helpers for constructing search queries to the backend. */
public class SearchUtil {
    public static final class Names {
        public static final String categories = "categories.id";
        public static final String priceFull = "variants.price.centAmount";
        public static final String centAmount = ".centAmount";
    }

    public static final class QueryParamNames {
        public static final String price = "price";
    }

    // ------------------------------------------------------------------
    // Filters
    // ------------------------------------------------------------------

    /** Creates a query parameter for a {@link io.sphere.client.filters.expressions.FilterExpression}. */
    public static List<QueryParam> createFilterParams(FilterType filterType, String attribute, String queryString) {
        switch (filterType) {
            case RESULTS_AND_FACETS: return list(new QueryParam("filter.query", attribute + ":" + queryString));
            case RESULTS: return list(new QueryParam("filter", attribute + ":" + queryString));
            case FACETS: return list(new QueryParam("filter.facets", attribute + ":" + queryString));
            case SMART: return list(
                    new QueryParam("filter", attribute + ":" + queryString),
                    new QueryParam("filter.facets", attribute + ":" + queryString));
            default: return list(new QueryParam("filter.query", attribute + ":" + queryString));
        }
    }

    // ------------------------------------------------------------------
    // Facets
    // ------------------------------------------------------------------

    /** Creates a query parameter for a {@link io.sphere.client.facets.expressions.FacetExpression}. */
    public static QueryParam createTermFacetParam(String attribute) {
        return new QueryParam("facet", attribute);
    }

    /** Creates a query parameter for a {@link io.sphere.client.facets.expressions.FacetExpression}. */
    public static QueryParam createRangeFacetParam(String attribute, String ranges) {
        return new QueryParam("facet", attribute + (Strings.isNullOrEmpty(ranges) ? "" : ":range " + ranges));
    }

    // ------------------------------------------------------------------
    // Upper bound adjustment for range facets
    // ------------------------------------------------------------------
    // ElasticSearch range facets are always [lower_inclusive, upper_exclusive),
    // without any possibility of configuration.
    // This doesn't match how our filters work: [lower_inclusive, upper_inclusive].
    // To get around this, we adjust the upper bound of facet ranges manually.
    // For example, if a user defines a facet [0, 10], [10, 20] we transform these
    // intervals into [0, 10.001), [10, 20.001) for the facet, and keep [0, 10], [10, 20]
    // for filters.
    // As a result, a product with the exact value of 10 will be counted (by the facet)
    // and returned (by the filter) for both intervals, which makes typical multiselect
    // facets behave in a consistent way.

    // Long: smallest step 1
    public static long adjustLongForSearch(long upperEndpoint) {
        return upperEndpoint + 1;
    }
    public static long adjustLongBackFromSearch(long searchUpperEndpoint) {
        return searchUpperEndpoint - 1;
    }

    // Double: smallest step 0.001 and round all numbers to 3 decimal places
    public static double adjustDoubleForSearch(double upperEndpoint) {
        BigDecimal rounded = new BigDecimal(upperEndpoint).setScale(3, RoundingMode.HALF_UP);
        return rounded.add(new BigDecimal(0.001)).setScale(3, RoundingMode.HALF_UP).doubleValue();
    }
    public static double adjustDoubleBackFromSearch(double searchUpperEndpoint) {
        return new BigDecimal(searchUpperEndpoint).subtract(new BigDecimal(0.001)).setScale(3, RoundingMode.HALF_UP).doubleValue();
    }

    // DateTime: smallest step 1ms
    public static DateTime adjustDateTimeForSearch(DateTime upperEndpoint) {
        return upperEndpoint.plus(Duration.millis(1));
    }
    public static DateTime adjustDateTimeBackFromSearch(DateTime searchUpperEndpoint) {
        return searchUpperEndpoint.minus(Duration.millis(1));
    }

    public static Function<Range<Long>, Range<Long>> adjustLongFacetRange = new Function<Range<Long>, Range<Long>>() {
        @Override public Range<Long> apply(@Nullable Range<Long> range) {
            if (!range.hasUpperBound()) return range;
            return range.span(Ranges.closed(range.upperEndpoint(), adjustLongForSearch(range.upperEndpoint())));
        }
    };

    public static Function<Range<Double>, Range<Double>> adjustDoubleFacetRange = new Function<Range<Double>, Range<Double>>() {
        @Override public Range<Double> apply(@Nullable Range<Double> range) {
            if (!range.hasUpperBound()) return range;
            return range.span(Ranges.closed(range.upperEndpoint(), adjustDoubleForSearch(range.upperEndpoint())));
        }
    };

    public static Function<Range<DateTime>, Range<DateTime>> adjustDateTimeFacetRange = new Function<Range<DateTime>, Range<DateTime>>() {
        @Override public Range<DateTime> apply(@Nullable Range<DateTime> range) {
            if (!range.hasUpperBound()) return range;
            return range.span(Ranges.closed(range.upperEndpoint(), adjustDateTimeForSearch(range.upperEndpoint())));
        }
    };




    // ------------------------------------------------------------------
    // Sort
    // ------------------------------------------------------------------

    private static final QueryParam qpPriceAsc = new QueryParam("sort", "price asc");
    private static final QueryParam qpPriceDesc = new QueryParam("sort", "price desc");
    // 'Pattern-match' on the sort object.
    // Another (more type safe) option would be to have ProductSort.createParam().
    // That method  would, however, need to be public and pollute the public API.
    public static QueryParam createSortParam(ProductSort sort) {
        if (sort == ProductSort.relevance) return null;
        if (sort == ProductSort.price.asc) return qpPriceAsc;
        if (sort == ProductSort.price.desc) return qpPriceDesc;
        throw new IllegalArgumentException("Unknown sort: " + sort);
    }

    // ------------------------------------------------------------------
    // Categories
    // ------------------------------------------------------------------

    public static ImmutableList<String> getCategoryIds(boolean includeSubcategories, Iterable<Category> categories) {
        if (categories == null) {
            return ImmutableList.of();
        }
        ImmutableList.Builder<String> builder = ImmutableList.builder();
        for (Category c: categories) {
            if (c == null) continue;
            if (includeSubcategories) {
                for (Category subCategory: getSubtree(c)) {
                    builder.add(subCategory.getId());
                }
            } else {
                builder.add(c.getId());
            }
        }
        return builder.build();
    }

    /** Returns a list of the whole subtree for this category, starting with the category itself. */
    private static List<Category> getSubtree(Category category) {
        List<Category> subtree = new ArrayList<Category>();
        addSubtreeRecursive(category, subtree);
        return subtree;
    }
    private static void addSubtreeRecursive(Category root, List<Category> list) {
        if (root == null) return;
        list.add(root);
        for (Category child: root.getChildren()) {
            addSubtreeRecursive(child, list);
        }
    }

    // ------------------------------------------------------------------
    // Backend query construction
    // ------------------------------------------------------------------

    public static String formatRange(String range) {
        return "range" + range;
    }

    /** Joins strings using ','. */
    public static final Joiner joinCommas = Joiner.on(',');

    /** Quotes a string. */
    public static final Function<String, String> addQuotes = new Function<String, String>() {
        public String apply(String s) {
            return "\"" + s + "\"";
        }
    };

    public static final Function<String, String> stringToParam = new Function<String, String>() {
        public String apply(String value) {
            return addQuotes.apply(value);
        }
    };

    public static final Function<Double, String> doubleToParam = new Function<Double, String>() {
        public String apply(Double value) {
            return value.toString();
        }
    };

    public static final Function<BigDecimal, String> decimalToParam = new Function<BigDecimal, String>() {
        public String apply(BigDecimal value) {
            return value.toString();
        }
    };


    public static final Function<DateTime, String> dateTimeToParam = new Function<DateTime, String>() {
        public String apply(DateTime dateTime) {
            return addQuotes.apply(ISODateTimeFormat.dateTime().print(dateTime.withZone(DateTimeZone.UTC)));
        }
    };

    public static final Function<BigDecimal, Long> toCents = new Function<BigDecimal, Long>() {
        public Long apply(BigDecimal amount) {
            return Money.amountToCents(amount);
        }
    };

    // ------------------------------------------------------------------
    // Backend query construction - ranges
    // ------------------------------------------------------------------

    public static final Function<Range<Double>, String> doubleRangeToParam = new Function<Range<Double>, String>() {
        public String apply(Range<Double> range) {
            return rangeToParam(range);
        }
    };

    public static final Function<Range<Long>, String> longRangeToParam = new Function<Range<Long>, String>() {
        public String apply(Range<Long> range) {
            return rangeToParam(range);
        }
    };

    public static final Function<Range<BigDecimal>, String> decimalRangeToParam = new Function<Range<BigDecimal>, String>() {
        public String apply(Range<BigDecimal> range) {
            return rangeToParam(range);
        }
    };

    public static final Function<Range<DateTime>, String> dateTimeRangeToParam = new Function<Range<DateTime>, String>() {
        public String apply(Range<DateTime> range) {
            if (range == null)
                throw new IllegalArgumentException("range");
            String f = range.hasLowerBound() ? dateTimeToParam.apply(range.lowerEndpoint()) : "*";
            String t = range.hasUpperBound() ? dateTimeToParam.apply(range.upperEndpoint()) : "*";
            return "(" + f + " to " + t + ")";
        }
    };

    public static <T extends Comparable> String rangeToParam(Range<T> range) {
        if (range == null)
            throw new IllegalArgumentException("range");
        String f = range.hasLowerBound() ? range.lowerEndpoint().toString() : "*";
        String t = range.hasUpperBound() ? range.upperEndpoint().toString() : "*";
        return "(" + f + " to " + t + ")";
    }

    /** Multiplies range by 100 and rounds to integer (conversion from units to 'cents'). */
    public static Function<Range<BigDecimal>, Range<Long>> toCentRange = new Function<Range<BigDecimal>, Range<Long>>() {
        public Range<Long> apply(Range<BigDecimal> range) {
            if (range == null)
                return null;
            Range<Long> downTo = range.hasLowerBound() ?
                    Ranges.downTo(toCents.apply(range.lowerEndpoint()), range.lowerBoundType()) :
                    Ranges.<Long>all();
            Range<Long> upTo = range.hasUpperBound() ?
                    Ranges.upTo(toCents.apply(range.upperEndpoint()), range.upperBoundType()) :
                    Ranges.<Long>all();
            return downTo.intersection(upTo);
        }
    };
}
