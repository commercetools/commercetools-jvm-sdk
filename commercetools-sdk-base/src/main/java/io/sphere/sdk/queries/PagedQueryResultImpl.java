package io.sphere.sdk.queries;

import com.fasterxml.jackson.annotation.JsonCreator;

import java.util.List;

class PagedQueryResultImpl<T> extends PagedResultBase<T> implements PagedQueryResult<T> {

    @JsonCreator
    PagedQueryResultImpl(final Long offset, final Long limit, final Long total, final List<T> results, final Long count) {
        super(offset, limit, total, results, count);
    }
}
