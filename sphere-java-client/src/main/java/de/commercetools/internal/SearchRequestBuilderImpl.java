package de.commercetools.internal;

import de.commercetools.sphere.client.*;
import de.commercetools.sphere.client.model.SearchResult;

import com.google.common.base.*;
import com.google.common.collect.FluentIterable;
import com.google.common.collect.Range;
import com.google.common.util.concurrent.ListenableFuture;
import org.joda.time.*;
import org.codehaus.jackson.type.TypeReference;
import static de.commercetools.internal.util.FilterUtil.*;

import java.util.Collection;

// dates:
// date     yyyy-MM-dd                  ISODateTimeFormat.date().print(ld)
// time     HH:mm:ss.SSS                ISODateTimeFormat.time().print(lt)
// datetime yyyy-MM-ddTHH:mm:ss.SSSZZ   ISODateTimeFormat.dateTime().print(dt.withZone(DateTimeZone.UTC))

/** {@inheritDoc} */
public class SearchRequestBuilderImpl<T> implements SearchRequestBuilder<T> {
    private Collection<Filter> filters;
    private RequestHolder<SearchResult<T>> requestHolder;
    private TypeReference<SearchResult<T>> jsonParserTypeRef;
    private int pageSize = Defaults.pageSize;
    private int page = 0;

    public SearchRequestBuilderImpl(
            Collection<Filter> filters, RequestHolder<SearchResult<T>> requestHolder, TypeReference<SearchResult<T>> jsonParserTypeRef) {
        this.filters = filters;
        this.requestHolder = requestHolder;
        this.jsonParserTypeRef = jsonParserTypeRef;
        for (Filter filter: filters) {
            QueryParam queryParam = filter.createQueryParam();
            if (queryParam != null) {
                requestHolder.addQueryParameter(queryParam.getName(), queryParam.getValue());
            }
        }
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

//    public SearchRequestBuilder<T> multiSelectStringFacet(String expression, Collection<String> values) {
//        return this.
//                facet(expression).
//                filterAnyString(expression, values, FilterType.RESULTS_ONLY).
//                filterAnyString(expression, values, FilterType.FACETS_ONLY);
//    }
//
//    public SearchRequestBuilder<T> multiSelectDateRangeFacet(String expression, Collection<Range<LocalDate>> ranges, Collection<Range<LocalDate>> allRanges) {
//        return this.
//                facetDateRanges(expression, allRanges).
//                filterDateRanges(expression, ranges, FilterType.RESULTS_ONLY).
//                filterDateRanges(expression, ranges, FilterType.FACETS_ONLY);
//    }

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
        requestHolder.addQueryParameter("limit", Integer.toString(this.pageSize));
        requestHolder.addQueryParameter("offset", Integer.toString(this.page * this.pageSize));
        return RequestExecutor.execute(requestHolder, jsonParserTypeRef);
    }
}
