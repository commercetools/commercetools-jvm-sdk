package de.commercetools.internal.util;

import com.google.common.base.Function;
import com.google.common.base.Joiner;
import com.google.common.base.Predicate;
import com.google.common.base.Strings;
import com.google.common.collect.FluentIterable;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Range;
import com.google.common.collect.Ranges;
import de.commercetools.internal.Defaults;
import de.commercetools.sphere.client.filters.expressions.FilterType;
import de.commercetools.sphere.client.QueryParam;
import de.commercetools.sphere.client.shop.model.Category;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.LocalDate;
import org.joda.time.LocalTime;
import org.joda.time.format.ISODateTimeFormat;

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
        public static final String price = "variants.price";
        public static final String centAmount = ".centAmount";
    }

    // ------------------------------------------------------------------
    // Filters
    // ------------------------------------------------------------------

    /** Creates a query parameter for a {@link de.commercetools.sphere.client.filters.expressions.FilterExpression}. */
    public static QueryParam createFilterParam(FilterType filterType, String attribute, String queryString) {
        return new QueryParam(filterTypeToString(filterType), attribute + ":" + queryString);
    }

    public static String filterTypeToString(FilterType filterType) {
        switch (filterType) {
            case RESULTS_AND_FACETS: return "filter.query";
            case RESULTS: return "filter";
            case FACETS: return "filter.facets";
            default: return "filter.query"; // to satisfy the compiler
        }
    }

    // ------------------------------------------------------------------
    // Facets
    // ------------------------------------------------------------------

    /** Creates a query parameter for a {@link de.commercetools.sphere.client.facets.expressions.FacetExpression}. */
    public static QueryParam createTermFacetParam(String attribute) {
        return new QueryParam("facet", attribute);
    }

    /** Creates a query parameter for a {@link de.commercetools.sphere.client.facets.expressions.FacetExpression}. */
    public static QueryParam createRangeFacetParam(String attribute, String ranges) {
        return new QueryParam("facet", attribute + (Strings.isNullOrEmpty(ranges) ? "" : ":range " + ranges));
    }

    public static List<QueryParam> createValueFacetParams(String attribute, Iterable<String> values) {
        List<QueryParam> queryParams = new ArrayList<QueryParam>();
        for (String value: values) {
            // use 'attribute_--_unquoted value' as alias
            String unquotedValue = (value.startsWith("\"") && value.endsWith("\"")) ? value.substring(1, value.length() - 1) : value;
            String alias = attribute + Defaults.valueFacetAliasSeparator + unquotedValue;
            queryParams.add(new QueryParam("facet", attribute + ":" + value + " as " + alias));
        }
        return queryParams;
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
            if (c != null) {
                if (includeSubcategories) {
                    for (Category subCategory: getSubtree(c)) {
                        builder.add(subCategory.getId());
                    }
                } else {
                    builder.add(c.getId());
                }
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
    // Formatting
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


    public static final Function<LocalDate, String> dateToParam = new Function<LocalDate, String>() {
        public String apply(LocalDate date) {
            return addQuotes.apply(ISODateTimeFormat.date().print(date));
        }
    };

    public static final Function<LocalTime, String> timeToParam = new Function<LocalTime, String>() {
        public String apply(LocalTime time) {
            return addQuotes.apply(ISODateTimeFormat.time().print(time));
        }
    };

    public static final Function<DateTime, String> dateTimeToParam = new Function<DateTime, String>() {
        public String apply(DateTime dateTime) {
            return addQuotes.apply(ISODateTimeFormat.dateTime().print(dateTime.withZone(DateTimeZone.UTC)));
        }
    };

    private static final BigDecimal hundred = new BigDecimal(100);
    public static final Function<BigDecimal, BigDecimal> toCents = new Function<BigDecimal, BigDecimal>() {
        public BigDecimal apply(BigDecimal money) {
            return money.multiply(hundred).setScale(0, RoundingMode.HALF_UP);
        }
    };

    // ------------------------------------------------------------------
    // Range formatting
    // ------------------------------------------------------------------

    public static final Function<Range<Double>, String> doubleRangeToParam = new Function<Range<Double>, String>() {
        public String apply(Range<Double> range) {
            return rangeToParam(range);
        }
    };

    public static final Function<Range<BigDecimal>, String> decimalRangeToParam = new Function<Range<BigDecimal>, String>() {
        public String apply(Range<BigDecimal> range) {
            return rangeToParam(range);
        }
    };

    public static final Function<Range<LocalDate>, String> dateRangeToParam = new Function<Range<LocalDate>, String>() {
        public String apply(Range<LocalDate> range) {
            if (range == null)
                throw new IllegalArgumentException("range");
            String f = range.hasLowerBound() ? dateToParam.apply(range.lowerEndpoint()) : "*";
            String t = range.hasUpperBound() ? dateToParam.apply(range.upperEndpoint()) : "*";
            return "(" + f + " to " + t + ")";
        }
    };

    public static final Function<Range<LocalTime>, String> timeRangeToParam = new Function<Range<LocalTime>, String>() {
        public String apply(Range<LocalTime> range) {
            if (range == null)
                throw new IllegalArgumentException("range");
            String f = range.hasLowerBound() ? timeToParam.apply(range.lowerEndpoint()) : "*";
            String t = range.hasUpperBound() ? timeToParam.apply(range.upperEndpoint()) : "*";
            return "(" + f + " to " + t + ")";
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
    public static Function<Range<BigDecimal>, Range<BigDecimal>> toMoneyRange = new Function<Range<BigDecimal>, Range<BigDecimal>>() {
        public Range<BigDecimal> apply(Range<BigDecimal> range) {
            if (range == null)
                return null;
            Range<BigDecimal> downTo = range.hasLowerBound() ?
                    Ranges.downTo(toCents.apply(range.lowerEndpoint()), range.lowerBoundType()) :
                    Ranges.<BigDecimal>all();
            Range<BigDecimal> upTo = range.hasUpperBound() ?
                    Ranges.upTo(toCents.apply(range.upperEndpoint()), range.upperBoundType()) :
                    Ranges.<BigDecimal>all();
            return downTo.intersection(upTo);
        }
    };

    // ------------------------------------------------------------------
    // Boolean checks
    // ------------------------------------------------------------------

    /** Returns true if given string is not null or empty. */
    public static final Predicate<String> isNotEmpty = new Predicate<String>() {
        public boolean apply(String s) {
            return !Strings.isNullOrEmpty(s);
        }
    };

    /** Returns true if given object is not null. */
    public static final Predicate<Object> isNotNull = new Predicate<Object>() {
        public boolean apply(Object o) {
            return o != null;
        }
    };

    public static final Predicate<Range<Double>> isDoubleRangeNotEmpty = SearchUtil.<Double>isRangeNotEmpty();
    public static final Predicate<Range<BigDecimal>> isDecimalRangeNotEmpty = SearchUtil.<BigDecimal>isRangeNotEmpty();
    public static final Predicate<Range<LocalDate>> isDateRangeNotEmpty = SearchUtil.<LocalDate>isRangeNotEmpty();
    public static final Predicate<Range<LocalTime>> isTimeRangeNotEmpty = SearchUtil.<LocalTime>isRangeNotEmpty();
    public static final Predicate<Range<DateTime>> isDateTimeRangeNotEmpty = SearchUtil.<DateTime>isRangeNotEmpty();

    /** Returns true if given range is not null and has at least one endpoint. */
    public static <T extends Comparable> Predicate<Range<T>> isRangeNotEmpty() {
        return new Predicate<Range<T>>() {
            public boolean apply(Range<T> range) {
                return (range != null && (range.hasLowerBound() || range.hasUpperBound()));
            }
        };
    }

    // ------------------------------------------------------------------
    // Lists
    // ------------------------------------------------------------------

    /** Helper for creating a list containing a single facet parameter. */
    public static ImmutableList<QueryParam> list(QueryParam param) {
        return ImmutableList.of(param);
    }

    /** Combines query params into a single list. */
    public static ImmutableList<QueryParam> list(List<QueryParam> params, QueryParam... additionalParams) {
        List<QueryParam> notNullParams = new ArrayList<QueryParam>();
        for (QueryParam p: additionalParams) {
            if (p != null) notNullParams.add(p);
        }
        return ImmutableList.<QueryParam>builder().addAll(FluentIterable.from(params).filter(isNotNull)).addAll(notNullParams).build();
    }
}