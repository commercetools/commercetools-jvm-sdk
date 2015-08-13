package io.sphere.sdk.expansion;

import java.util.List;

public interface ReferenceExpansionSupport<T> {
    List<ExpansionPath<T>> expansionPaths();
}
