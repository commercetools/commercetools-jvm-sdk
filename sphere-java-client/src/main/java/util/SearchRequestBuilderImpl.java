package de.commercetools.sphere.client.util;

import com.google.common.base.*;
import com.google.common.collect.FluentIterable;
import com.google.common.collect.Range;
import com.google.common.collect.Ranges;
import com.google.common.util.concurrent.ListenableFuture;
import com.ning.http.client.AsyncCompletionHandler;
import com.ning.http.client.Response;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;

import de.commercetools.sphere.client.async.ListenableFutureAdapter;
import de.commercetools.sphere.client.BackendException;
import de.commercetools.sphere.client.model.SearchResult;

import java.util.Collection;

/** @inheritdoc */
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

    /** @inheritdoc */
    public SearchRequestBuilder<T> limit(int limit) {
        requestHolder.addQueryParameter("limit", Integer.toString(limit));
        return this;
    }

    /** @inheritdoc */
    public SearchRequestBuilder<T> offset(int offset) {
        requestHolder.addQueryParameter("offset", Integer.toString(offset));
        return this;
    }

    /** @inheritdoc */
    public SearchRequestBuilderImpl<T> expand(String... paths) {
        for (String path: paths) {
            requestHolder.addQueryParameter("expand", path);
        }
        return this;
    }

    
    // ----------------------------------------------------------
    // Facet
    // ----------------------------------------------------------

    /** @inheritdoc */
    public SearchRequestBuilder<T> facet(String expression) {
        if (Strings.isNullOrEmpty(expression))
            throw new IllegalArgumentException("Please provide a non-empty facet expression.");
        requestHolder.addQueryParameter("facet", expression);
        return this;
    }

    /** @inheritdoc */
    public SearchRequestBuilder<T> facetRanges(String expression, Collection<Range<Double>> ranges) {
        if (Strings.isNullOrEmpty(expression))
            throw new IllegalArgumentException("Please provide a non-empty facet expression.");
        String joinedRanges = joinCommas.join(FluentIterable.from(ranges).transform(rangeToString));
        requestHolder.addQueryParameter("facet.range", expression + ":range " + joinedRanges);
        return this;
    }


    // ----------------------------------------------------------
    // Filters
    // ----------------------------------------------------------

    /** @inheritdoc */
    public SearchRequestBuilder<T> filter(String path, String value) {
        return filter(path, value, defaultFilterType);
    }

    /** @inheritdoc */
    public SearchRequestBuilder<T> filter(String path, Double value) {
        return filter(path, value, defaultFilterType);
    }

    /** @inheritdoc */
    public SearchRequestBuilder<T> filterMoney(String path, Double value) {
        return filterMoney(path, value, defaultFilterType);
    }

    // ----------------------------------------------------------
    // Filters with type
    // ----------------------------------------------------------

    /** @inheritdoc */
    public SearchRequestBuilder<T> filter(String path, String value, FilterType filterType) {
        if (Strings.isNullOrEmpty(value)) return this;
        requestHolder.addQueryParameter(filterTypeToString(filterType), path + ":" + addQuotes.apply(value));
        return this;
    }

    /** @inheritdoc */
    public SearchRequestBuilder<T> filter(String path, Double value, FilterType filterType) {
        if (value == null) return this;
        requestHolder.addQueryParameter(filterTypeToString(filterType), path + ":" + value);
        return this;
    }

    /** @inheritdoc */
    public SearchRequestBuilder<T> filterMoney(String path, Double value, FilterType filterType) {
        if (value == null) return this;
        return filter(path + ".centAmount", Math.floor(value * 100), filterType);
    }


    // ----------------------------------------------------------
    // Range filters
    // ----------------------------------------------------------

    /** @inheritdoc */
    public SearchRequestBuilder<T> filterRange(String path, Range<Double> range) {
        return filterRange(path, range, defaultFilterType);
    }

    /** @inheritdoc */
    public SearchRequestBuilder<T> filterRanges(String path, Collection<Range<Double>> ranges) {
        return filterRanges(path, ranges, defaultFilterType);
    }

    /** @inheritdoc */
    public SearchRequestBuilder<T> filterMoneyRange(String path, Range<Double> range) {
        return filterMoneyRange(path, range, defaultFilterType);
    }

    /** @inheritdoc */
    public SearchRequestBuilder<T> filterMoneyRanges(String path, Collection<Range<Double>> ranges) {
        return filterMoneyRanges(path, ranges, defaultFilterType);
    }

    // ----------------------------------------------------------
    // Range filters with type
    // ----------------------------------------------------------

    /** @inheritdoc */
    public SearchRequestBuilder<T> filterRange(String path, Range<Double> range, FilterType filterType) {
        if (!isRangeNotEmpty.apply(range)) return this;
        requestHolder.addQueryParameter(filterTypeToString(filterType), path + ":range " + rangeToString.apply(range));
        return this;
    }

    /** @inheritdoc */
    public SearchRequestBuilder<T> filterRanges(String path, Collection<Range<Double>> ranges, FilterType filterType) {
        String joinedRanges = joinCommas.join(FluentIterable.from(ranges).filter(isRangeNotEmpty).transform(rangeToString));
        if (Strings.isNullOrEmpty(joinedRanges)) return this;
        requestHolder.addQueryParameter(filterTypeToString(filterType), path + ":range " + joinedRanges);
        return this;
    }
    
    /** @inheritdoc */
    public SearchRequestBuilder<T> filterMoneyRange(String path, Range<Double> range, FilterType filterType) {
        return filterRange(path + ".centAmount", toMoneyRange.apply(range), filterType); 
    }

    /** @inheritdoc */
    public SearchRequestBuilder<T> filterMoneyRanges(String path, Collection<Range<Double>> ranges, FilterType filterType) {
        return filterRanges(path, FluentIterable.from(ranges).transform(toMoneyRange).toImmutableList(), filterType);
    }


    // ----------------------------------------------------------
    // Multiple value filters
    // ----------------------------------------------------------

    /** @inheritdoc */
    public SearchRequestBuilder<T> filterAnyString(String path, Collection<String> values) {
        return filterAnyString(path, values, defaultFilterType);
    }

    /** @inheritdoc */
    public SearchRequestBuilder<T> filterAnyDouble(String path, Collection<Double> values) {
        return filterAnyDouble(path, values, defaultFilterType);
    }

    // filterAnyMoney

    // ----------------------------------------------------------
    // Multiple value filters with type
    // ----------------------------------------------------------

    /** @inheritdoc */
    public SearchRequestBuilder<T> filterAnyString(String path, Collection<String> values, FilterType filterType) {
        String joinedValues = joinCommas.join(FluentIterable.from(values).filter(isNotEmpty).transform(addQuotes));
        if (Strings.isNullOrEmpty(joinedValues)) return this;
        requestHolder.addQueryParameter(filterTypeToString(filterType), path + ":" + joinedValues);
        return this;
    }

    /** @inheritdoc */
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

    /** @inheritdoc */
    public SearchResult<T> fetch() throws BackendException {
        try {
            return fetchAsync().get();
        } catch(Exception ex) {
            throw new BackendException(ex);
        }
    }

    /** @inheritdoc */
    public ListenableFuture<SearchResult<T>> fetchAsync() throws BackendException {
        try {
            if (!Strings.isNullOrEmpty(fullTextQuery)) {
                requestHolder.addQueryParameter("text", fullTextQuery);
            }
            if (Log.isTraceEnabled()) {
                Log.trace(requestHolder.getRawUrl());
            }
            return new ListenableFutureAdapter<SearchResult<T>>(requestHolder.executeRequest(new AsyncCompletionHandler<SearchResult<T>>() {
                @Override
                public SearchResult<T> onCompleted(Response response) throws Exception {
                    if (response.getStatusCode() != 200) {
                        String message = String.format(
                                "The backend returned an error response: %s\n[%s]\n%s",
                                requestHolder.getRawUrl(),
                                response.getStatusCode(),
                                response.getResponseBody(Charsets.UTF_8.name())
                        );
                        Log.error(message);
                        throw new BackendException(message);
                    } else {
                        ObjectMapper jsonParser = new ObjectMapper();
                        SearchResult<T> parsed = jsonParser.readValue(response.getResponseBody(Charsets.UTF_8.name()), jsonParserTypeRef);
                        if (Log.isTraceEnabled()) {
                            Log.trace(Util.prettyPrintJsonString(response.getResponseBody(Charsets.UTF_8.name())));
                        }
                        return parsed;
                    }
                }
            }));
        } catch (Exception e) {
            throw new BackendException(e);
        }
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

    /** Converts range to string format understood by the backend web service. */
    private static final Function<Range<Double>, String> rangeToString = new Function<Range<Double>, String>() {
        public String apply(Range<Double> range) {
            if (range == null)
                throw new IllegalArgumentException("range");
            String f = range.hasLowerBound() ? range.lowerEndpoint().toString() : "*";
            String t = range.hasUpperBound() ? range.upperEndpoint().toString() : "*";
            return String.format("(%s to %s)", f, t);
        }
    };

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
    private static Function<Range<Double>, Range<Double>> toMoneyRange = new Function<Range<Double>, Range<Double>>() {
        public Range<Double> apply(Range<Double> range) {
            if (range == null)
                return null;
            Range<Double> downTo = range.hasLowerBound() ?
                    Ranges.downTo(Math.floor(range.lowerEndpoint() * 100), range.lowerBoundType()) : 
                    Ranges.<Double>all();
            Range<Double> upTo = range.hasUpperBound() ?
                    Ranges.upTo(Math.floor(range.upperEndpoint() * 100), range.upperBoundType()) :
                    Ranges.<Double>all();
            return downTo.intersection(upTo);
        }
    };
}
