package de.commercetools.sphere.client.util;

/** The way a search filter influences facet counts. */
public enum FilterType {
  /** The filter restricts the result set as well as facet counts. */
  DEFAULT,
  /** The filter restricts the result set but has no effect on facets. */
  RESULTS_ONLY,
  /** The filter does not restrict the result set itself.
   * It only has effect on facet counts of all facets except the facet with identical path expression. */
  FACETS_ONLY
}
