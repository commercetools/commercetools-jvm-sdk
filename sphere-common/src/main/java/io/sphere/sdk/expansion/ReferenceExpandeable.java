package io.sphere.sdk.expansion;

import java.util.List;

public interface ReferenceExpandeable<T> {
    List<ExpansionPath<T>> expansionPaths();
}
