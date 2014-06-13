package io.sphere.sdk.queries;

import com.fasterxml.jackson.core.type.TypeReference;
import io.sphere.sdk.client.Requestable;

public interface Query<I, R> extends Requestable {
    TypeReference<PagedQueryResult<R>> typeReference();
}
