package de.commercetools.internal.request;

import de.commercetools.internal.Defaults;
import de.commercetools.internal.util.Log;
import de.commercetools.sphere.client.*;
import de.commercetools.sphere.client.model.SearchResult;

import com.google.common.util.concurrent.ListenableFuture;
import org.codehaus.jackson.type.TypeReference;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

// dates:
// date     yyyy-MM-dd                  ISODateTimeFormat.date().print(ld)
// time     HH:mm:ss.SSS                ISODateTimeFormat.time().print(lt)
// datetime yyyy-MM-ddTHH:mm:ss.SSSZZ   ISODateTimeFormat.dateTime().print(dt.withZone(DateTimeZone.UTC))

/** {@inheritDoc} */
public class SearchRequestImpl<T> implements SearchRequest<T> {
    private Iterable<FilterExpression> filters;
    private RequestHolder<SearchResult<T>> requestHolder;
    private TypeReference<SearchResult<T>> jsonParserTypeRef;
    private int pageSize = Defaults.pageSize;
    private int page = 0;

    public SearchRequestImpl(
            Iterable<FilterExpression> filters, RequestHolder<SearchResult<T>> requestHolder, TypeReference<SearchResult<T>> jsonParserTypeRef) {
        this.filters = filters;
        this.requestHolder = requestHolder;
        this.jsonParserTypeRef = jsonParserTypeRef;
        for (FilterExpression filter: filters) {
            if (filter == null) {
                Log.warn("Null filter found to filters collection.");
                continue;  // be tolerant in what we accept
            }
            QueryParam queryParam = filter.createQueryParam();
            if (queryParam != null) {
                requestHolder.addQueryParameter(queryParam.getName(), queryParam.getValue());
            }
        }
    }

    public SearchRequest<T> page(int page) {
        // added to the query parameters in fetchAsync()
        this.page = page;
        return this;
    }

    public SearchRequest<T> pageSize(int pageSize) {
        // added to the query parameters in fetchAsync()
        this.pageSize = pageSize;
        return this;
    }

    public SearchRequestImpl<T> expand(String... paths) {
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

    public SearchRequest<T> faceted(FacetExpression... facets) {
        return faceted(Arrays.asList(facets));
    }

    public SearchRequest<T> faceted(Collection<FacetExpression> facets) {
        for (FacetExpression facet: facets) {
            List<QueryParam> queryParams = facet.createQueryParams();
            for (QueryParam queryParam: queryParams) {
                this.requestHolder.addQueryParameter(queryParam.getName(), queryParam.getValue());
            }
        }
        return this;
    }

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
