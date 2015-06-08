package io.sphere.sdk.queries;

import java.util.List;

public interface ReferenceExpandeableDsl<T, C> extends ReferenceExpandeable<T> {
    /**
     * Creates a new object with the properties of the old object but replaces all expansion paths with {@code expansionPaths}.
     * @param expansionPaths the new expansion paths
     * @return new object
     */
    C withExpansionPath(final List<ExpansionPath<T>> expansionPaths);

    /**
     * Creates a new object with the properties of the old object but adds {@code expansionPath} to the expansion paths.
     * @param expansionPath the new expansion path to add to the existing ones
     * @return new object
     */
    C plusExpansionPath(final ExpansionPath<T> expansionPath);

    /**
     * Creates a new object with the properties of the old object but replaces all expansion paths with a single {@code expansionPath}.
     * @param expansionPath the new expansion paths
     * @return new object
     */
    C withExpansionPath(final ExpansionPath<T> expansionPath);
}
