package io.sphere.sdk.search;

import java.util.Optional;

public interface SearchModel<T> {

    Optional<String> getPathSegment();

    Optional<? extends SearchModel<T>> getParent();
}
