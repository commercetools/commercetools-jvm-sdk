package de.commercetools.internal.request;

import com.google.common.base.Function;
import com.google.common.util.concurrent.Futures;
import de.commercetools.internal.Defaults;
import de.commercetools.internal.util.Log;
import de.commercetools.internal.util.SearchResultUtil;
import de.commercetools.sphere.client.*;
import de.commercetools.sphere.client.facets.expressions.FacetExpression;
import de.commercetools.sphere.client.filters.expressions.FilterExpression;
import de.commercetools.sphere.client.model.SearchResult;
import static de.commercetools.internal.util.ListUtil.list;

import com.google.common.util.concurrent.ListenableFuture;
import org.codehaus.jackson.type.TypeReference;

import javax.annotation.Nullable;
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

    public SearchRequestImpl(RequestHolder<SearchResult<T>> requestHolder, TypeReference<SearchResult<T>> jsonParserTypeRef) {
        this.requestHolder = requestHolder;
        this.jsonParserTypeRef = jsonParserTypeRef;
    }

    @Override public SearchRequest<T> page(int page) {
        // added to the query parameters in fetchAsync()
        this.page = page;
        return this;
    }

    @Override public SearchRequest<T> pageSize(int pageSize) {
        // added to the query parameters in fetchAsync()
        this.pageSize = pageSize;
        return this;
    }

// not implemented in the backend yet
//    public SearchRequestImpl<T> expand(String... paths) {
//        for (String path: paths) {
//            requestHolder.addQueryParameter("expand", path);
//        }
//        return this;
//    }

    /** The URL the request will be sent to, for debugging purposes. */
    public String getRawUrl() {
        return this.requestHolder.getRawUrl();
    }

    @Override public SearchRequest<T> filtered(FilterExpression filter, FilterExpression... filters) {
        return filtered(list(filter, filters));
    }

    @Override public SearchRequest<T> filtered(Iterable<FilterExpression> filters) {
        for (FilterExpression filter: filters) {
            if (filter == null) {
                Log.warn("Null filter passed to SearchRequest.filtered(), ignoring.");
                continue;  // be tolerant in what we accept
            }
            for (QueryParam qp: filter.createQueryParams()) {
                requestHolder.addQueryParameter(qp.getName(), qp.getValue());
            }
        }
        return this;
    }

    @Override public SearchRequest<T> faceted(FacetExpression facet, FacetExpression... facets) {
        return faceted(list(facet, facets));
    }

    @Override public SearchRequest<T> faceted(Collection<FacetExpression> facets) {
        for (FacetExpression facet: facets) {
            if (facet == null) {
                Log.warn("Null facet passed to SearchRequest.faceted(), ignoring.");
                continue;  // be tolerant in what we accept
            }
            List<QueryParam> queryParams = facet.createQueryParams();
            for (QueryParam queryParam: queryParams) {
                this.requestHolder.addQueryParameter(queryParam.getName(), queryParam.getValue());
            }
        }
        return this;
    }

    @Override public SearchResult<T> fetch() throws SphereException {
        try {
            return fetchAsync().get();
        } catch(Exception ex) {
            throw new SphereException(ex);
        }
    }

    @Override public ListenableFuture<SearchResult<T>> fetchAsync() throws SphereException {
        requestHolder.addQueryParameter("limit", Integer.toString(this.pageSize));
        requestHolder.addQueryParameter("offset", Integer.toString(this.page * this.pageSize));
        return Futures.transform(RequestExecutor.executeAndThrowOnError(requestHolder, jsonParserTypeRef), new Function<SearchResult<T>, SearchResult<T>>() {
            @Override public SearchResult<T> apply(@Nullable SearchResult<T> res) {
                if (res == null) return null;
                // fill in page size, keep results intact
                return SearchResultUtil.transform(res, res.getResults(), SearchRequestImpl.this.pageSize);
            }
        });
    }

    @Override public String getUrl() {
        return this.requestHolder.getRawUrl();
    }
}
