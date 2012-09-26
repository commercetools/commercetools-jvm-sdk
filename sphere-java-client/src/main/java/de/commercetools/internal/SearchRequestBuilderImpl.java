package de.commercetools.internal;

import de.commercetools.sphere.client.SphereException;
import de.commercetools.sphere.client.model.SearchResult;
import de.commercetools.sphere.client.util.SearchRequestBuilder;
import de.commercetools.sphere.client.util.FilterType;

import com.google.common.base.*;
import com.google.common.collect.FluentIterable;
import com.google.common.collect.Range;
import com.google.common.collect.Ranges;
import com.google.common.util.concurrent.ListenableFuture;
import org.joda.time.*;
import org.codehaus.jackson.type.TypeReference;
import org.joda.time.format.ISODateTimeFormat;

import java.util.Collection;

// dates:
// date     yyyy-MM-dd                  ISODateTimeFormat.date().print(ld)
// time     HH:mm:ss.SSS                ISODateTimeFormat.time().print(lt)
// datetime yyyy-MM-ddTHH:mm:ss.SSSZZ   ISODateTimeFormat.dateTime().print(dt.withZone(DateTimeZone.UTC))

/** {@inheritDoc} */
public class SearchRequestBuilderImpl<T> implements SearchRequestBuilder<T> {
    private String fullTextQuery;
    private RequestHolder<SearchResult<T>> requestHolder;
    private TypeReference<SearchResult<T>> jsonParserTypeRef;
    private int pageSize = Defaults.pageSize;
    private int page = 0;

    /** Default behavior of filters when no specified. */
    private static final FilterType defaultFilterType = FilterType.DEFAULT;

    public SearchRequestBuilderImpl(
            String fullTextQuery, RequestHolder<SearchResult<T>> requestHolder, TypeReference<SearchResult<T>> jsonParserTypeRef) {
        this.fullTextQuery = fullTextQuery;
        this.requestHolder = requestHolder;
        this.jsonParserTypeRef = jsonParserTypeRef;
    }

    public SearchRequestBuilder<T> page(int page) {
        // added to the query parameters in fetchAsync()
        this.page = page;
        return this;
    }

    public SearchRequestBuilder<T> pageSize(int pageSize) {
        // added to the query parameters in fetchAsync()
        this.pageSize = pageSize;
        return this;
    }

    public SearchRequestBuilderImpl<T> expand(String... paths) {
        for (String path: paths) {
            requestHolder.addQueryParameter("expand", path);
        }
        return this;
    }

    /** The URL the request will be sent to, for debugging purposes. */
    public String getRawUrl() {
        return this.requestHolder.getRawUrl();
    }

    // ----------------------------------------------------------
    // Facet
    // ----------------------------------------------------------

    public SearchRequestBuilder<T> facet(String expression) {
        if (Strings.isNullOrEmpty(expression))
            throw new IllegalArgumentException("Please provide a non-empty facet expression.");
        requestHolder.addQueryParameter("facet", expression);
        return this;
    }

    public SearchRequestBuilder<T> facetDoubleRanges(String expression, Collection<Range<Double>> ranges) {
        if (Strings.isNullOrEmpty(expression))
            throw new IllegalArgumentException("Please provide a non-empty facet expression.");
        String joinedRanges = joinCommas.join(FluentIterable.from(ranges).transform(doubleRangeToString));
        requestHolder.addQueryParameter("facet", expression + ":range" + joinedRanges);
        return this;
    }

    public SearchRequestBuilder<T> facetStringRanges(String expression, Collection<Range<String>> ranges) {
        if (Strings.isNullOrEmpty(expression))
            throw new IllegalArgumentException("Please provide a non-empty facet expression.");
        String joinedRanges = joinCommas.join(FluentIterable.from(ranges).transform(stringRangeToString));
        requestHolder.addQueryParameter("facet", expression + ":range" + joinedRanges);
        return this;
    }

    public SearchRequestBuilder<T> facetDateRanges(String expression, Collection<Range<LocalDate>> ranges) {
        if (Strings.isNullOrEmpty(expression))
            throw new IllegalArgumentException("Please provide a non-empty facet expression.");
        String joinedRanges = joinCommas.join(FluentIterable.from(ranges).transform(dateRangeToString));
        requestHolder.addQueryParameter("facet", expression + ":range" + joinedRanges);
        return this;
    }

    public SearchRequestBuilder<T> multiSelectStringFacet(String expression, Collection<String> values) {
        return this.
                facet(expression).
                filterAnyString(expression, values, FilterType.RESULTS_ONLY).
                filterAnyString(expression, values, FilterType.FACETS_ONLY);
    }

    public SearchRequestBuilder<T> multiSelectDateRangeFacet(String expression, Collection<Range<LocalDate>> ranges, Collection<Range<LocalDate>> allRanges) {
        return this.
                facetDateRanges(expression, allRanges).
                filterDateRanges(expression, ranges, FilterType.RESULTS_ONLY).
                filterDateRanges(expression, ranges, FilterType.FACETS_ONLY);
    }

    // ----------------------------------------------------------
    // Filters
    // ----------------------------------------------------------

    public SearchRequestBuilder<T> filter(String path, String value) {
        return filter(path, value, defaultFilterType);
    }

    public SearchRequestBuilder<T> filter(String path, Double value) {
        return filter(path, value, defaultFilterType);
    }

    public SearchRequestBuilder<T> filterMoney(String path, Double value) {
        return filterMoney(path, value, defaultFilterType);
    }

    // ----------------------------------------------------------
    // Filters with type
    // ----------------------------------------------------------

    public SearchRequestBuilder<T> filter(String path, String value, FilterType filterType) {
        if (Strings.isNullOrEmpty(value)) return this;
        requestHolder.addQueryParameter(filterTypeToString(filterType), path + ":" + addQuotes.apply(value));
        return this;
    }

    public SearchRequestBuilder<T> filter(String path, Double value, FilterType filterType) {
        if (value == null) return this;
        requestHolder.addQueryParameter(filterTypeToString(filterType), path + ":" + value);
        return this;
    }

    public SearchRequestBuilder<T> filterMoney(String path, Double value, FilterType filterType) {
        // Don't reuse filter() because centAmount is an integer and therefore needs to be formatted as integers
        if (value == null) return this;
        requestHolder.addQueryParameter(filterTypeToString(filterType), path + ".centAmount:" + (int)(value * 100));
        return this;
    }


    // ----------------------------------------------------------
    // Range filters
    // ----------------------------------------------------------

    public SearchRequestBuilder<T> filterRange(String path, Range<Double> range) {
        return filterRange(path, range, defaultFilterType);
    }

    public SearchRequestBuilder<T> filterStringRange(String path, Range<String> range) {
        requestHolder.addQueryParameter("filter", path + ":range " + rangeToStringQuoted(range));
        return this;
    }

    public SearchRequestBuilder<T> filterRanges(String path, Collection<Range<Double>> ranges) {
        return filterRanges(path, ranges, defaultFilterType);
    }

    public SearchRequestBuilder<T> filterDateRanges(String path, Collection<Range<LocalDate>> ranges) {
        return filterDateRanges(path, ranges, defaultFilterType);
    }

    public SearchRequestBuilder<T> filterMoneyRange(String path, Range<Double> range) {
        return filterMoneyRange(path, range, defaultFilterType);
    }

    public SearchRequestBuilder<T> filterMoneyRanges(String path, Collection<Range<Double>> ranges) {
        return filterMoneyRanges(path, ranges, defaultFilterType);
    }

    // ----------------------------------------------------------
    // Range filters with type
    // ----------------------------------------------------------

    public SearchRequestBuilder<T> filterRange(String path, Range<Double> range, FilterType filterType) {
        if (!isDoubleRangeNotEmpty.apply(range)) return this;
        requestHolder.addQueryParameter(filterTypeToString(filterType), path + ":range " + rangeToString(range));
        return this;
    }

    public SearchRequestBuilder<T> filterRanges(String path, Collection<Range<Double>> ranges, FilterType filterType) {
        String joinedRanges = joinCommas.join(FluentIterable.from(ranges).filter(isDoubleRangeNotEmpty).transform(doubleRangeToString));
        if (Strings.isNullOrEmpty(joinedRanges)) return this;
        requestHolder.addQueryParameter(filterTypeToString(filterType), path + ":range " + joinedRanges);
        return this;
    }

    public SearchRequestBuilder<T> filterDateRanges(String path, Collection<Range<LocalDate>> ranges, FilterType filterType) {
        String joinedRanges = joinCommas.join(FluentIterable.from(ranges).filter(isDateRangeNotEmpty).transform(dateRangeToString));
        if (Strings.isNullOrEmpty(joinedRanges)) return this;
        requestHolder.addQueryParameter(filterTypeToString(filterType), path + ":range " + joinedRanges);
        return this;
    }

    public SearchRequestBuilder<T> filterMoneyRange(String path, Range<Double> range, FilterType filterType) {
        // Don't reuse filterRange() because money ranges are Range<Integer> and therefore need to be formatted as integers
        if (!isDoubleRangeNotEmpty.apply(range)) return this;
        requestHolder.addQueryParameter(filterTypeToString(filterType), path + ".centAmount:range " + rangeToString(toMoneyRange.apply(range)));
        return this;
    }

    public SearchRequestBuilder<T> filterMoneyRanges(String path, Collection<Range<Double>> ranges, FilterType filterType) {
        // Don't reuse filterRanges() because money ranges are Range<Integer> and therefore need to be formatted as integers
        String joinedRanges = joinCommas.join(FluentIterable.from(ranges).filter(isDoubleRangeNotEmpty).transform(toMoneyRange).transform(intRangeToString));
        if (Strings.isNullOrEmpty(joinedRanges)) return this;
        requestHolder.addQueryParameter(filterTypeToString(filterType), path + ".centAmount:range " + joinedRanges);
        return this;
    }


    // ----------------------------------------------------------
    // Multiple value filters
    // ----------------------------------------------------------

    public SearchRequestBuilder<T> filterAnyString(String path, Collection<String> values) {
        return filterAnyString(path, values, defaultFilterType);
    }

    public SearchRequestBuilder<T> filterAnyDouble(String path, Collection<Double> values) {
        return filterAnyDouble(path, values, defaultFilterType);
    }

    // filterAnyMoney

    // ----------------------------------------------------------
    // Multiple value filters with type
    // ----------------------------------------------------------

    public SearchRequestBuilder<T> filterAnyString(String path, Collection<String> values, FilterType filterType) {
        String joinedValues = joinCommas.join(FluentIterable.from(values).filter(isNotEmpty).transform(addQuotes));
        if (Strings.isNullOrEmpty(joinedValues)) return this;
        requestHolder.addQueryParameter(filterTypeToString(filterType), path + ":" + joinedValues);
        return this;
    }

    public SearchRequestBuilder<T> filterAnyDouble(String path, Collection<Double> values, FilterType filterType) {
        String joinedValues = joinCommas.join(FluentIterable.from(values).filter(isNotNull));
        if (Strings.isNullOrEmpty(joinedValues)) return this;
        requestHolder.addQueryParameter(filterTypeToString(filterType), path + ":" + joinedValues);
        return this;
    }

    // filterAnyMoney


    // ---------------------------------------
    // Fetch
    // ---------------------------------------

    public SearchResult<T> fetch() throws SphereException {
        try {
            return fetchAsync().get();
        } catch(Exception ex) {
            throw new SphereException(ex);
        }
    }

    public ListenableFuture<SearchResult<T>> fetchAsync() throws SphereException {
        if (!Strings.isNullOrEmpty(fullTextQuery)) {
            requestHolder.addQueryParameter("text", fullTextQuery);
        }
        requestHolder.addQueryParameter("limit", Integer.toString(this.pageSize));
        requestHolder.addQueryParameter("offset", Integer.toString(this.page * this.pageSize));
        return RequestExecutor.execute(requestHolder, jsonParserTypeRef);
    }


    // ---------------------------------------
    // Helpers
    // ---------------------------------------

    /** Joins strings using ','. */
    private static final Joiner joinCommas = Joiner.on(',');

    private String filterTypeToString(FilterType filterType) {
        switch (filterType) {
            case DEFAULT: return "filter.query";
            case RESULTS_ONLY: return "filter";
            case FACETS_ONLY: return "filter.facets";
            default: return "filter.query"; // to satisfy the compiler
        }
    }

    /** Converts a double range to string format understood by the backend web service. */
    private static final Function<Range<Double>, String> doubleRangeToString = new Function<Range<Double>, String>() {
        public String apply(Range<Double> range) {
            return rangeToString(range);
        }
    };

    /** Converts and int range to string format for the backend web service. */
    private static final Function<Range<Integer>, String> intRangeToString = new Function<Range<Integer>, String>() {
        public String apply(Range<Integer> range) {
            return rangeToString(range);
        }
    };

    /** Converts and int range to string format for the backend web service. */
    private static final Function<Range<String>, String> stringRangeToString = new Function<Range<String>, String>() {
        public String apply(Range<String> range) {
            return rangeToStringQuoted(range);
        }
    };

    /** Converts and int range to string format for the backend web service. */
    private static final Function<Range<LocalDate>, String> dateRangeToString = new Function<Range<LocalDate>, String>() {
        public String apply(Range<LocalDate> range) {
            if (range == null)
                throw new IllegalArgumentException("range");
            String f = range.hasLowerBound() ? "\"" + ISODateTimeFormat.date().print(range.lowerEndpoint()) + "\"" : "*";
            String t = range.hasUpperBound() ? "\"" + ISODateTimeFormat.date().print(range.upperEndpoint()) + "\"" : "*";
            return "(" + f + " to " + t + ")";
        }
    };

    /** Converts a range to string format understood by the backend web service. */
    private static <T extends Comparable> String rangeToString(Range<T> range) {
        if (range == null)
            throw new IllegalArgumentException("range");
        String f = range.hasLowerBound() ? range.lowerEndpoint().toString() : "*";
        String t = range.hasUpperBound() ? range.upperEndpoint().toString() : "*";
        return "(" + f + " to " + t + ")";
    }

    /** Converts a range to string format understood by the backend web service. */
    private static <T extends Comparable> String rangeToStringQuoted(Range<T> range) {
        if (range == null)
            throw new IllegalArgumentException("range");
        String f = range.hasLowerBound() ? "\"" + range.lowerEndpoint().toString() + "\"" : "*";
        String t = range.hasUpperBound() ? "\"" + range.upperEndpoint().toString() + "\"" : "*";
        return "(" + f + " to " + t + ")";
    }

    /** Adds quotes to a string. */
    private static final Function<String, String> addQuotes = new Function<String, String>() {
        public String apply(String s) {
            return "\"" + s + "\"";
        }
    };

    /** Returns true if given string is not null or empty. */
    private static final Predicate<String> isNotEmpty = new Predicate<String>() {
        public boolean apply(String s) {
            return !Strings.isNullOrEmpty(s);
        }
    };

    /** Returns true if given object is not null. */
    private static final Predicate<Object> isNotNull = new Predicate<Object>() {
        public boolean apply(Object o) {
            return o != null;
        }
    };

    private static final Predicate<Range<Double>> isDoubleRangeNotEmpty = SearchRequestBuilderImpl.<Double>isRangeNotEmpty();

    private static final Predicate<Range<LocalDate>> isDateRangeNotEmpty = SearchRequestBuilderImpl.<LocalDate>isRangeNotEmpty();

    /** Returns true if given range is not null and has at least one endpoint. */
    private static <T extends Comparable> Predicate<Range<T>> isRangeNotEmpty() {
        return new Predicate<Range<T>>() {
            public boolean apply(Range<T> range) {
                return (range != null && (range.hasLowerBound() || range.hasUpperBound()));
            }
        };
    }

    /** Multiplies range by 100 and rounds to integer (conversion from units to 'cents'). */
    private static Function<Range<Double>, Range<Integer>> toMoneyRange = new Function<Range<Double>, Range<Integer>>() {
        public Range<Integer> apply(Range<Double> range) {
            if (range == null)
                return null;
            Range<Integer> downTo = range.hasLowerBound() ?
                    Ranges.downTo((int)(range.lowerEndpoint() * 100), range.lowerBoundType()) :
                    Ranges.<Integer>all();
            Range<Integer> upTo = range.hasUpperBound() ?
                    Ranges.upTo((int)(range.upperEndpoint() * 100), range.upperBoundType()) :
                    Ranges.<Integer>all();
            return downTo.intersection(upTo);
        }
    };
}
