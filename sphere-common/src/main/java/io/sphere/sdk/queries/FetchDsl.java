package io.sphere.sdk.queries;

import java.util.List;

public interface FetchDsl<T, C> extends Fetch<T>, ReferenceExpandeableDsl<T, C> {
    List<ExpansionPath<T>> expansionPaths();


}
