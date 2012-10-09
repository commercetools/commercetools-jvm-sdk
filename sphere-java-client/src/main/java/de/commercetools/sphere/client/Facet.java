package de.commercetools.sphere.client;

import java.util.List;

/** Expresses what attributes to facet on when fetching products, such as 'attributes.color'. */
public interface Facet {
    /** Sphere HTTP API query parameter that this facet expression will get turned into. Mostly for debugging purposes. */
    List<QueryParam> createQueryParams();
}
