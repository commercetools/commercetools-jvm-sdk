package de.commercetools.internal;

import de.commercetools.sphere.client.async.ListenableFutureAdapter;
import de.commercetools.sphere.client.BackendException;
import de.commercetools.sphere.client.model.SearchResult;
import de.commercetools.sphere.client.util.SearchRequestBuilder;
import de.commercetools.sphere.client.util.FilterType;
import de.commercetools.sphere.client.util.Util;
import de.commercetools.sphere.client.util.Log;

import com.google.common.base.*;
import com.google.common.collect.FluentIterable;
import com.google.common.collect.Range;
import com.google.common.collect.Ranges;
import com.google.common.util.concurrent.ListenableFuture;
import com.ning.http.client.AsyncCompletionHandler;
import com.ning.http.client.Response;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;
import java.util.Collection;

// dates:
// date     yyyy-MM-dd                  ISODateTimeFormat.date.print(ld)
// time     HH:mm:ss.SSS                ISODateTimeFormat.time.print(lt)
// datetime yyyy-MM-ddTHH:mm:ss.SSSZZ   ISODateTimeFormat.dateTime.print(dt.withZone(DateTimeZone.UTC))

/** {@inheritDoc} */
public class SearchRequestBuilderImpl<T> implements SearchRequestBuilder<T> {
    private String fullTextQuery;
    private RequestHolder<SearchResult<T>> requestHolder;
    private TypeReference<SearchResult<T>> jsonParserTypeRef;

    /** Default behavior of filters when no specified. */
    private static final FilterType defaultFilterType = FilterType.DEFAULT;

    public SearchRequestBuilderImpl(
            String fullTextQuery, RequestHolder<SearchResult<T>> requestHolder, TypeReference<SearchResult<T>> jsonParserTypeRef) {
        this.fullTextQuery = fullTextQuery;
        this.requestHolder = requestHolder;
        this.jsonParserTypeRef = jsonParserTypeRef;
    }

    /** {@inheritDoc} */
    public SearchRequestBuilder<T> limit(int limit) {
        requestHolder.addQueryParameter("limit", Integer.toString(limit));
        return this;
    }

    /** {@inheritDoc} */
    public SearchRequestBuilder<T> offset(int offset) {
        requestHolder.addQueryParameter("offset", Integer.toString(offset));
        return this;
    }

    /** {@inheritDoc} */
    public SearchRequestBuilderImpl<T> expand(String... paths) {
        for (String path: paths) {
            requestHolder.addQueryParameter("expand", path);
        }
        return this;
    }


    // ----------------------------------------------------------
    // Facet
    // ----------------------------------------------------------

    /** {@inheritDoc} */
    public SearchRequestBuilder<T> facet(String expression) {
        if (Strings.isNullOrEmpty(expression))
            throw new IllegalArgumentException("Please provide a non-empty facet expression.");
        requestHolder.addQueryParameter("facet", expression);
        return this;
    }

    /** {@inheritDoc} */
    public SearchRequestBuilder<T> facetDoubleRanges(String expression, Collection<Range<Double>> ranges) {
        if (Strings.isNullOrEmpty(expression))
            throw new IllegalArgumentException("Please provide a non-empty facet expression.");
        String joinedRanges = joinCommas.join(FluentIterable.from(ranges).transform(doubleRangeToString));
        requestHolder.addQueryParameter("facet", expression + ":range" + joinedRanges);
        return this;
    }

    /** {@inheritDoc} */
    public SearchRequestBuilder<T> facetStringRanges(String expression, Collection<Range<String>> ranges) {
        if (Strings.isNullOrEmpty(expression))
            throw new IllegalArgumentException("Please provide a non-empty facet expression.");
        String joinedRanges = joinCommas.join(FluentIterable.from(ranges).transform(stringRangeToString));
        requestHolder.addQueryParameter("facet", expression + ":range" + joinedRanges);
        return this;
    }


    // ----------------------------------------------------------
    // Filters
    // ----------------------------------------------------------

    /** {@inheritDoc} */
    public SearchRequestBuilder<T> filter(String path, String value) {
        return filter(path, value, defaultFilterType);
    }

    /** {@inheritDoc} */
    public SearchRequestBuilder<T> filter(String path, Double value) {
        return filter(path, value, defaultFilterType);
    }

    /** {@inheritDoc} */
    public SearchRequestBuilder<T> filterMoney(String path, Double value) {
        return filterMoney(path, value, defaultFilterType);
    }

    // ----------------------------------------------------------
    // Filters with type
    // ----------------------------------------------------------

    /** {@inheritDoc} */
    public SearchRequestBuilder<T> filter(String path, String value, FilterType filterType) {
        if (Strings.isNullOrEmpty(value)) return this;
        requestHolder.addQueryParameter(filterTypeToString(filterType), path + ":" + addQuotes.apply(value));
        return this;
    }

    /** {@inheritDoc} */
    public SearchRequestBuilder<T> filter(String path, Double value, FilterType filterType) {
        if (value == null) return this;
        requestHolder.addQueryParameter(filterTypeToString(filterType), path + ":" + value);
        return this;
    }

    /** {@inheritDoc} */
    public SearchRequestBuilder<T> filterMoney(String path, Double value, FilterType filterType) {
        // Don't reuse filter because centAmount is an integer and therefore needs to be formatted as integers
        if (value == null) return this;
        requestHolder.addQueryParameter(filterTypeToString(filterType), path + ".centAmount:" + (int)(value * 100));
        return this;
    }


    // ----------------------------------------------------------
    // Range filters
    // ----------------------------------------------------------

    /** {@inheritDoc} */
    public SearchRequestBuilder<T> filterRange(String path, Range<Double> range) {
        return filterRange(path, range, defaultFilterType);
    }

    public SearchRequestBuilder<T> filterStringRange(String path, Range<String> range) {
        requestHolder.addQueryParameter("filter", path + ":range " + rangeToString(range, "(\"%s\" to \"%s\")"));
        return this;
    }

    /** {@inheritDoc} */
    public SearchRequestBuilder<T> filterRanges(String path, Collection<Range<Double>> ranges) {
        return filterRanges(path, ranges, defaultFilterType);
    }

    /** {@inheritDoc} */
    public SearchRequestBuilder<T> filterMoneyRange(String path, Range<Double> range) {
        return filterMoneyRange(path, range, defaultFilterType);
    }

    /** {@inheritDoc} */
    public SearchRequestBuilder<T> filterMoneyRanges(String path, Collection<Range<Double>> ranges) {
        return filterMoneyRanges(path, ranges, defaultFilterType);
    }

    // ----------------------------------------------------------
    // Range filters with type
    // ----------------------------------------------------------

    /** {@inheritDoc} */
    public SearchRequestBuilder<T> filterRange(String path, Range<Double> range, FilterType filterType) {
        if (!isRangeNotEmpty.apply(range)) return this;
        requestHolder.addQueryParameter(filterTypeToString(filterType), path + ":range " + rangeToString(range));
        return this;
    }

    /** {@inheritDoc} */
    public SearchRequestBuilder<T> filterRanges(String path, Collection<Range<Double>> ranges, FilterType filterType) {
        String joinedRanges = joinCommas.join(FluentIterable.from(ranges).filter(isRangeNotEmpty).transform(doubleRangeToString));
        if (Strings.isNullOrEmpty(joinedRanges)) return this;
        requestHolder.addQueryParameter(filterTypeToString(filterType), path + ":range " + joinedRanges);
        return this;
    }

    /** {@inheritDoc} */
    public SearchRequestBuilder<T> filterMoneyRange(String path, Range<Double> range, FilterType filterType) {
        // Don't reuse filterRange() because money ranges are Range<Integer> and therefore need to be formatted as integers
        if (!isRangeNotEmpty.apply(range)) return this;
        requestHolder.addQueryParameter(filterTypeToString(filterType), path + ".centAmount:range " + rangeToString(toMoneyRange.apply(range)));
        return this;
    }

    /** {@inheritDoc} */
    public SearchRequestBuilder<T> filterMoneyRanges(String path, Collection<Range<Double>> ranges, FilterType filterType) {
        // Don't reuse filteRanges() because money ranges are Range<Integer> and therefore need to be formatted as integers
        String joinedRanges = joinCommas.join(FluentIterable.from(ranges).filter(isRangeNotEmpty).transform(toMoneyRange).transform(intRangeToString));
        if (Strings.isNullOrEmpty(joinedRanges)) return this;
        requestHolder.addQueryParameter(filterTypeToString(filterType), path + ".centAmount:range " + joinedRanges);
        return this;
    }


    // ----------------------------------------------------------
    // Multiple value filters
    // ----------------------------------------------------------

    /** {@inheritDoc} */
    public SearchRequestBuilder<T> filterAnyString(String path, Collection<String> values) {
        return filterAnyString(path, values, defaultFilterType);
    }

    /** {@inheritDoc} */
    public SearchRequestBuilder<T> filterAnyDouble(String path, Collection<Double> values) {
        return filterAnyDouble(path, values, defaultFilterType);
    }

    // filterAnyMoney

    // ----------------------------------------------------------
    // Multiple value filters with type
    // ----------------------------------------------------------

    /** {@inheritDoc} */
    public SearchRequestBuilder<T> filterAnyString(String path, Collection<String> values, FilterType filterType) {
        String joinedValues = joinCommas.join(FluentIterable.from(values).filter(isNotEmpty).transform(addQuotes));
        if (Strings.isNullOrEmpty(joinedValues)) return this;
        requestHolder.addQueryParameter(filterTypeToString(filterType), path + ":" + joinedValues);
        return this;
    }

    /** {@inheritDoc} */
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

    /** {@inheritDoc} */
    public SearchResult<T> fetch() throws BackendException {
        try {
            return fetchAsync().get();
        } catch(Exception ex) {
            throw new BackendException(ex);
        }
    }

    /** {@inheritDoc} */
    public ListenableFuture<SearchResult<T>> fetchAsync() throws BackendException {
        if (!Strings.isNullOrEmpty(fullTextQuery)) {
            requestHolder.addQueryParameter("text", fullTextQuery);
        }
        return RequestHolders.execute(requestHolder, jsonParserTypeRef);
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

    /** Converts and int range to string format understood by the backend web service. */
    private static final Function<Range<Integer>, String> intRangeToString = new Function<Range<Integer>, String>() {
        public String apply(Range<Integer> range) {
            return rangeToString(range);
        }
    };

    /** Converts and int range to string format understood by the backend web service. */
    private static final Function<Range<String>, String> stringRangeToString = new Function<Range<String>, String>() {
        public String apply(Range<String> range) {
            return rangeToString(range, "(\"%s\" to \"%s\")");
        }
    };

    /** Converts a range to string format understood by the backend web service. */
    private static <T extends Comparable> String rangeToString(Range<T> range, String formatString) {
        if (range == null)
            throw new IllegalArgumentException("range");
        String f = range.hasLowerBound() ? range.lowerEndpoint().toString() : "*";
        String t = range.hasUpperBound() ? range.upperEndpoint().toString() : "*";
        return String.format(formatString, f, t);
    }

    private static <T extends Comparable> String rangeToString(Range<T> range) {
        return rangeToString(range, "(%s to %s)");
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

    /** Returns true if given range is not null and has at least one endpoint. */
    private static final Predicate<Range<Double>> isRangeNotEmpty = new Predicate<Range<Double>>() {
        public boolean apply(Range<Double> range) {
            return (range != null && (range.hasLowerBound() || range.hasUpperBound()));
        }
    };

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
