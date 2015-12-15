package io.sphere.sdk.expansion;

import java.util.List;

public interface ExpansionPathsHolder<T> {
    List<ExpansionPath<T>> getExpansionPaths();
}
