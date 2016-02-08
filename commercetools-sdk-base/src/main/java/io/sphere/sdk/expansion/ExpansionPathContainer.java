package io.sphere.sdk.expansion;

import java.util.List;

public interface ExpansionPathContainer<T> {
    List<ExpansionPath<T>> expansionPaths();
}
