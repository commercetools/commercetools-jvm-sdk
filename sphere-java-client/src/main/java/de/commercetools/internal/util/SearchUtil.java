package de.commercetools.internal.util;

import com.google.common.base.Function;
import com.google.common.base.Joiner;
import com.google.common.base.Predicate;
import com.google.common.base.Strings;
import com.google.common.collect.FluentIterable;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Range;
import com.google.common.collect.Ranges;
import de.commercetools.sphere.client.QueryParam;
import de.commercetools.sphere.client.FilterType;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.LocalDate;
import org.joda.time.LocalTime;
import org.joda.time.format.ISODateTimeFormat;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;

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

    /** Creates a query parameter for a {@link de.commercetools.sphere.client.Filter}. */
    public static QueryParam createFilterParam(FilterType filterType, String attribute, String queryString) {
        return new QueryParam(filterTypeToString(filterType), attribute + ":" + queryString);
    }

    public static String filterTypeToString(FilterType filterType) {
        switch (filterType) {
            case DEFAULT: return "filter.query";
            case RESULTS_ONLY: return "filter";
            case FACETS_ONLY: return "filter.facets";
            default: return "filter.query"; // to satisfy the compiler
        }
    }

    /** Creates a query parameter for a {@link de.commercetools.sphere.client.Facet}. */
    public static QueryParam createTermsFacetParam(String attribute) {
        return new QueryParam("facet", attribute);
    }

    /** Creates a query parameter for a {@link de.commercetools.sphere.client.Facet}. */
    public static QueryParam createValueFacetParam(String attribute, String values) {
        return new QueryParam("facet", attribute + (Strings.isNullOrEmpty(values) ? "" : ":" + values));
    }

    /** Creates a query parameter for a {@link de.commercetools.sphere.client.Facet}. */
    public static QueryParam createRangeFacetParam(String attribute, String ranges) {
        return new QueryParam("facet", attribute + (Strings.isNullOrEmpty(ranges) ? "" : ":range " + ranges));
    }

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

    public static final Function<LocalDate, String> dateToString = new Function<LocalDate, String>() {
        public String apply(LocalDate date) {
            return ISODateTimeFormat.date().print(date);
        }
    };

    public static final Function<LocalTime, String> timeToString = new Function<LocalTime, String>() {
        public String apply(LocalTime time) {
            return ISODateTimeFormat.time().print(time);
        }
    };

    public static final Function<DateTime, String> dateTimeToString = new Function<DateTime, String>() {
        public String apply(DateTime dateTime) {
            return ISODateTimeFormat.dateTime().print(dateTime.withZone(DateTimeZone.UTC));
        }
    };

    private static final BigDecimal hundred = new BigDecimal(100);
    public static final Function<BigDecimal, BigDecimal> toCents = new Function<BigDecimal, BigDecimal>() {
        public BigDecimal apply(BigDecimal money) {
            return money.multiply(hundred).setScale(0, RoundingMode.HALF_UP);
        }
    };

    // ------------------------------------------------------------------
    // Ranges
    // ------------------------------------------------------------------

    public static final Function<Range<Double>, String> doubleRangeToString = new Function<Range<Double>, String>() {
        public String apply(Range<Double> range) {
            return rangeToString(range);
        }
    };

    public static final Function<Range<BigDecimal>, String> decimalRangeToString = new Function<Range<BigDecimal>, String>() {
        public String apply(Range<BigDecimal> range) {
            return rangeToString(range);
        }
    };

    public static final Function<Range<String>, String> stringRangeToString = new Function<Range<String>, String>() {
        public String apply(Range<String> range) {
            return rangeToStringQuoted(range);
        }
    };

    public static final Function<Range<LocalDate>, String> dateRangeToString = new Function<Range<LocalDate>, String>() {
        public String apply(Range<LocalDate> range) {
            if (range == null)
                throw new IllegalArgumentException("range");
            String f = range.hasLowerBound() ? "\"" + dateToString.apply(range.lowerEndpoint()) + "\"" : "*";
            String t = range.hasUpperBound() ? "\"" + dateToString.apply(range.upperEndpoint()) + "\"" : "*";
            return "(" + f + " to " + t + ")";
        }
    };

    public static final Function<Range<LocalTime>, String> timeRangeToString = new Function<Range<LocalTime>, String>() {
        public String apply(Range<LocalTime> range) {
            if (range == null)
                throw new IllegalArgumentException("range");
            String f = range.hasLowerBound() ? "\"" + timeToString.apply(range.lowerEndpoint()) + "\"" : "*";
            String t = range.hasUpperBound() ? "\"" + timeToString.apply(range.upperEndpoint()) + "\"" : "*";
            return "(" + f + " to " + t + ")";
        }
    };

    public static final Function<Range<DateTime>, String> dateTimeRangeToString = new Function<Range<DateTime>, String>() {
        public String apply(Range<DateTime> range) {
            if (range == null)
                throw new IllegalArgumentException("range");
            String f = range.hasLowerBound() ? "\"" + dateTimeToString.apply(range.lowerEndpoint()) + "\"" : "*";
            String t = range.hasUpperBound() ? "\"" + dateTimeToString.apply(range.upperEndpoint()) + "\"" : "*";
            return "(" + f + " to " + t + ")";
        }
    };

    public static <T extends Comparable> String rangeToString(Range<T> range) {
        if (range == null)
            throw new IllegalArgumentException("range");
        String f = range.hasLowerBound() ? range.lowerEndpoint().toString() : "*";
        String t = range.hasUpperBound() ? range.upperEndpoint().toString() : "*";
        return "(" + f + " to " + t + ")";
    }

    public static <T extends Comparable> String rangeToStringQuoted(Range<T> range) {
        if (range == null)
            throw new IllegalArgumentException("range");
        String f = range.hasLowerBound() ? "\"" + range.lowerEndpoint().toString() + "\"" : "*";
        String t = range.hasUpperBound() ? "\"" + range.upperEndpoint().toString() + "\"" : "*";
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
        return ImmutableList.<QueryParam>builder().addAll(params).addAll(notNullParams).build();
    }

    /** Helper for vararg methods with at least one argument. */
    public static <T> ImmutableList<T> list(T t, T... ts) {
        ImmutableList.Builder<T> builder = ImmutableList.<T>builder();
        if (t != null) builder = builder.add(t);
        for (T elem: ts) {
            if (elem != null) builder.add(elem);
        }
        return builder.build();
    }

    /** Converts a Collection to a List. */
    public static <T> ImmutableList<T> toList(Iterable<T> elems) {
        if (elems instanceof ImmutableList) {
            return (ImmutableList<T>)elems;
        } else {
            return ImmutableList.copyOf(FluentIterable.from(elems).filter(isNotNull));
        }
    }
}
