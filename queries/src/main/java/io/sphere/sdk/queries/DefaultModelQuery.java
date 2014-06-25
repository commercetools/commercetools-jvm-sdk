package io.sphere.sdk.queries;

import com.fasterxml.jackson.core.type.TypeReference;

public class DefaultModelQuery<I, R extends I, M> extends QueryDslImpl<I, R, M>{
    public DefaultModelQuery(final String endpoint, final TypeReference<PagedQueryResult<R>> pagedQueryResultTypeReference) {
        super(endpoint, pagedQueryResultTypeReference);
    }
}
