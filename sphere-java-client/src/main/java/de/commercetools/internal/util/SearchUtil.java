package de.commercetools.internal.util;

import com.google.common.base.Function;
import com.google.common.base.Joiner;
import com.google.common.base.Predicate;
import com.google.common.base.Strings;
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

/** Query string formatting helpers for the {@link de.commercetools.sphere.client.Filters} class. */
public class SearchUtil {
    // dates:
    // date     yyyy-MM-dd                  ISODateTimeFormat.date().print(ld)
    // time     HH:mm:ss.SSS                ISODateTimeFormat.time().print(lt)
    // datetime yyyy-MM-ddTHH:mm:ss.SSSZZ   ISODateTimeFormat.dateTime().print(dt.withZone(DateTimeZone.UTC))

    /** Creates a query parameter for a {@link de.commercetools.sphere.client.Filter}. */
    public static QueryParam createFilterParam(FilterType filterType, String attribute, String queryString) {
        return new QueryParam(filterTypeToString(filterType), attribute + ":" + queryString);
    }

    /** Creates a query parameter for a {@link de.commercetools.sphere.client.Facet}. */
    public static QueryParam createTermsFacetParam(String attribute) {
        return new QueryParam("facet", attribute);
    }

    /** Creates a query parameter for a {@link de.commercetools.sphere.client.Facet}. */
    public static QueryParam createFacetParam(String attribute, String queryString) {
        return new QueryParam("facet", attribute + ":" + queryString);
    }

    /** Joins strings using ','. */
    public static final Joiner joinCommas = Joiner.on(',');

    public static String filterTypeToString(FilterType filterType) {
        switch (filterType) {
            case DEFAULT: return "filter.query";
            case RESULTS_ONLY: return "filter";
            case FACETS_ONLY: return "filter.facets";
            default: return "filter.query"; // to satisfy the compiler
        }
    }

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

    public static final Function<Double, Double> multiplyByHundred = new Function<Double, Double>() {
        @Override
        public Double apply(@Nullable Double value) {
            return Double.valueOf((int)(value * 100));
        }
    };

    public static final Function<Range<Double>, String> doubleRangeToString = new Function<Range<Double>, String>() {
        public String apply(Range<Double> range) {
            return rangeToString(range);
        }
    };

    public static final Function<Range<Integer>, String> intRangeToString = new Function<Range<Integer>, String>() {
        public String apply(Range<Integer> range) {
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
    public static Function<Range<Double>, Range<Integer>> toMoneyRange = new Function<Range<Double>, Range<Integer>>() {
        public Range<Integer> apply(Range<Double> range) {
            if (range == null)
                return null;
            Range<Integer> downTo = range.hasLowerBound() ?
                    Ranges.downTo((int) (range.lowerEndpoint() * 100), range.lowerBoundType()) :
                    Ranges.<Integer>all();
            Range<Integer> upTo = range.hasUpperBound() ?
                    Ranges.upTo((int)(range.upperEndpoint() * 100), range.upperBoundType()) :
                    Ranges.<Integer>all();
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
}
