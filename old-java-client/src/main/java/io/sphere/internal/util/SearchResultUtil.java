package io.sphere.internal.util;

import io.sphere.client.model.SearchResult;

import java.util.Collection;

public class SearchResultUtil {
    // static so that it does not show up in code completion on SearchResult
    /** Transforms the results of a SearchResult. */
    public static <T, R> SearchResult<R> transform(SearchResult<T> res, Collection<R> results) {
        return transform(res, results, null);
    }

    /** Sets the pageSize of a SearchResult, and potentially transforms results. */
    public static <T, R> SearchResult<R> transform(SearchResult<T> res, Collection<R> results, Integer pageSize) {
        if (results == null) throw new NullPointerException("results");
        if (results.size() != res.getResults().size())
            throw new IllegalArgumentException("When transforming a SearchResult, the number of results must stay unchanged.");
        return new SearchResult<R>(
                res.getOffset(),
                res.getCount(),
                res.getTotal(),
                results,
                res.getFacetsRaw(),
                pageSize != null ? pageSize : res.getPageSize());
    }
}
