package io.sphere.internal.request;

import com.google.common.base.Function;
import com.google.common.util.concurrent.Futures;
import io.sphere.internal.Defaults;
import io.sphere.internal.util.Log;
import io.sphere.internal.util.SearchResultUtil;
import io.sphere.internal.util.SearchUtil;
import io.sphere.internal.util.Util;
import io.sphere.client.*;
import io.sphere.client.facets.expressions.FacetExpression;
import io.sphere.client.filters.expressions.FilterExpression;
import io.sphere.client.model.SearchResult;
import static io.sphere.internal.util.ListUtil.list;

import com.google.common.util.concurrent.ListenableFuture;
import org.codehaus.jackson.type.TypeReference;

import javax.annotation.Nullable;
import java.util.Collection;
import java.util.List;


// dates:
// date     yyyy-MM-dd                  ISODateTimeFormat.date().print(ld)
// time     HH:mm:ss.SSS                ISODateTimeFormat.time().print(lt)
// datetime yyyy-MM-ddTHH:mm:ss.SSSZZ   ISODateTimeFormat.dateTime().print(dt.withZone(DateTimeZone.UTC))

public class SearchRequestImpl<T> implements SearchRequest<T>, TestableRequest {
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

    private void addQueryParam(QueryParam qp) {
        if (qp != null) {
            requestHolder.addQueryParameter(qp.getName(), qp.getValue());
        }
    }

    @Override public SearchRequest<T> filter(FilterExpression filter, FilterExpression... filters) {
        return filter(list(filter, filters));
    }

    @Override public SearchRequest<T> filter(Iterable<FilterExpression> filters) {
        for (FilterExpression filter: filters) {
            if (filter == null) {
                Log.warn("Null filter passed to SearchRequest.filter(), ignoring.");
                continue;  // be tolerant in what we accept
            }
            for (QueryParam qp: filter.createQueryParams()) {
                addQueryParam(qp);
            }
        }
        return this;
    }

    @Override public SearchRequest<T> facet(FacetExpression facet, FacetExpression... facets) {
        return facet(list(facet, facets));
    }

    @Override public SearchRequest<T> facet(Iterable<FacetExpression> facets) {
        for (FacetExpression facet: facets) {
            if (facet == null) {
                Log.warn("Null facet passed to SearchRequest.faceted(), ignoring.");
                continue;  // be tolerant in what we accept
            }
            List<QueryParam> queryParams = facet.createQueryParams();
            for (QueryParam qp: queryParams) {
                addQueryParam(qp);
            }
        }
        return this;
    }

    @Override public SearchRequest<T> sort(ProductSort sort) {
        addQueryParam(SearchUtil.createSortParam(sort));
        return this;
    }

    @Override public SearchResult<T> fetch() {
        return Util.sync(fetchAsync());
    }

    @Override public ListenableFuture<SearchResult<T>> fetchAsync() {
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

    // testing purposes
    @Override public TestableRequestHolder getRequestHolder() {
        return requestHolder;
    }

    // logging and debugging purposes
    @Override public String toString() {
        return getRequestHolder().toString();
    }
}
