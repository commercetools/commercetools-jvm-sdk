package io.sphere.sdk.queries;

import java.util.List;
import java.util.Objects;

import static io.sphere.sdk.utils.ListUtils.listOf;
import static java.util.Arrays.asList;

public interface ReferenceExpandeableDsl<T, R> extends ReferenceExpandeable<T> {
    R withExpansionPath(final List<ExpansionPath<T>> expansionPaths);

    default R plusExpansionPath(final ExpansionPath<T> expansionPath) {
        return withExpansionPath(listOf(expansionPaths(), expansionPath));
    }

    default R withExpansionPath(final ExpansionPath<T> expansionPath) {
        Objects.requireNonNull(expansionPath);
        return withExpansionPath(asList(expansionPath));
    }
}
