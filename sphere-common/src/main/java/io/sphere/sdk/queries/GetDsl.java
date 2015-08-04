package io.sphere.sdk.queries;

import io.sphere.sdk.expansion.ReferenceExpandeableDsl;

public interface GetDsl<R, T, C> extends Get<R>, ReferenceExpandeableDsl<T, C> {

}
