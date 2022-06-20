package io.sphere.sdk.expansion;

import java.util.List;

/**
 * Marker interface for Composable Commerce endpoints which support reference expansion
 * @param <T> aggregate root which has references which can expanded. Example: ProductProjection
 */
public interface ReferenceExpansionSupport<T> extends ExpansionPathContainer<T> {
    @Override
    List<ExpansionPath<T>> expansionPaths();
}
