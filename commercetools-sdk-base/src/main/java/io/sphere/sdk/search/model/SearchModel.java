package io.sphere.sdk.search.model;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static io.sphere.sdk.utils.SphereInternalUtils.toStream;
import static java.util.stream.Collectors.joining;

public interface SearchModel<T> {

    @Nullable
    String getPathSegment();

    @Nullable
    SearchModel<T> getParent();

    default String attributePath() {
        return toStream(buildPath()).collect(joining("."));
    }

    default List<String> buildPath() {
        final List<String> pathSegments = new ArrayList<>();
        Optional.ofNullable(getParent()).ifPresent(p -> pathSegments.addAll(p.buildPath()));
        Optional.ofNullable(getPathSegment()).ifPresent(ps -> pathSegments.add(ps));
        return pathSegments;
    }

    /**
     * Checks if the given pathSegments matches the path of the parents and the current one of this search model.
     *
     * @param pathSegments the path segments, the most closer element is one the right side
     * @return true, if the path segments of this matches exactly pathSegments
     */
    default boolean hasPath(final List<String> pathSegments) {
        return buildPath().equals(pathSegments);
    }
}
