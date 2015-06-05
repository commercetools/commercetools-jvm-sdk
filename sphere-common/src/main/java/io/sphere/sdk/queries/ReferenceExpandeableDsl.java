package io.sphere.sdk.queries;

import java.util.List;

public interface ReferenceExpandeableDsl<T, C> extends ReferenceExpandeable<T> {
    C withExpansionPath(final List<ExpansionPath<T>> expansionPaths);

    C plusExpansionPath(final ExpansionPath<T> expansionPath);

    C withExpansionPath(final ExpansionPath<T> expansionPath);
}
