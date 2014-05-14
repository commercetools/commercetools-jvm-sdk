package io.sphere.sdk.client;

import com.fasterxml.jackson.core.type.TypeReference;

public interface Query<I, R> extends Requestable {
    TypeReference<PagedQueryResult<R>> typeReference();
}
