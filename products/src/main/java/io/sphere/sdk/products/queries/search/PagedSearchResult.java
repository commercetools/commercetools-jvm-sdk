package io.sphere.sdk.products.queries.search;

import com.fasterxml.jackson.annotation.JsonCreator;
import io.sphere.sdk.queries.PagedResult;

import java.util.List;

public class PagedSearchResult<T> extends PagedResult<T> {

    @JsonCreator
    PagedSearchResult(final int offset, final int total, final List<T> results) {
        super(offset, total, results);
    }
}
