package io.sphere.sdk.products;

/**
 * Special role to pass the id of a {@link Product} or {@link ProductProjection} since {@link io.sphere.sdk.models.Identifiable} can only be implemented for one of them.
 */
public interface ProductIdentifiable {
    String getId();
}
