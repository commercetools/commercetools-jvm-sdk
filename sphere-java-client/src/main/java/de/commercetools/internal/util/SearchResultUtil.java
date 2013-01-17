package de.commercetools.internal.util;

import de.commercetools.sphere.client.model.SearchResult;

import java.util.Collection;

public class SearchResultUtil {
    // static so that it does not show up in code completion on SearchResult
    /** Creates a copy of the this searchResult, with different result items. */
    public static <T, R> SearchResult<R> transform(SearchResult<T> res, Collection<R> results) {
        return transform(res, results, null);
    }

    /** Creates a copy of the this searchResult, with modified result items and page size. */
    public static <T, R> SearchResult<R> transform(SearchResult<T> res, Collection<R> results, Integer pageSize) {
        if (results == null) throw new NullPointerException("results");
        if (results.size() != res.getResults().size())
            throw new IllegalArgumentException("When transforming a SearchResult, the number of results can't be changed.");
        return new SearchResult<R>(
                res.getOffset(),
                res.getCount(),
                res.getTotal(),
                results,
                res.getFacetsRaw(),
                pageSize != null ? pageSize : res.getPageSize());
    }
}
