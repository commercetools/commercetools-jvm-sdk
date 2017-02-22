package io.sphere.sdk.queries;

import io.sphere.sdk.models.Point;

/**
 * Query model for {@link io.sphere.sdk.models.GeoJSON} objects.
 *
 * @param <T> the type for which this predicate can be instantiated
 */
public interface GeoJSONQueryModel<T> extends QueryModel<T>, OptionalQueryModel<T> {

    /**
     * Creates a predicate for the {@code within predicate} for fields of type {@link io.sphere.sdk.models.GeoJSON}.
     *
     * @param center the center pont (longitude, latitude)
     * @param radius the radius of the circle
     *
     * @return the query predicate for the given parameters
     */
    QueryPredicate<T> withinCircle(final Point center, final Double radius);
}
