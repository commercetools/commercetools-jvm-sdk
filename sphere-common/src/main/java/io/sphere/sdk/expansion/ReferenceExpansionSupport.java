package io.sphere.sdk.expansion;

import java.util.List;

/**
 * Marker interface for commercetools endpoints which support reference expansion
 * @param <T> aggegate root which has references which can expanded. Example: ProductProjection
 */
public interface ReferenceExpansionSupport<T> extends ExpansionPathContainer<T> {
    @Override
    List<ExpansionPath<T>> expansionPaths();
}
