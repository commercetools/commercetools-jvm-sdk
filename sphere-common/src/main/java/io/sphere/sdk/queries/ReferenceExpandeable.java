package io.sphere.sdk.queries;

import java.util.List;

public interface ReferenceExpandeable<T> {
    List<ExpansionPath<T>> expansionPaths();
}
