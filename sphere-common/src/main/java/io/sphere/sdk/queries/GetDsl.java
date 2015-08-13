package io.sphere.sdk.queries;

import io.sphere.sdk.expansion.ReferenceExpansionDsl;

public interface GetDsl<R, T, C> extends Get<R>, ReferenceExpansionDsl<T, C> {

}
