package de.commercetools.sphere.client.filters.expressions;

/** The way a search filter influences facet counts. */
public enum FilterType {
  /** The filter restricts the result set as well as all facets. This is the default. */
  RESULTS_AND_FACETS,
  /** The filter restricts the result set but has no effect on facets. */
  RESULTS,
  /** The filter does not restrict the result set itself.
   *  It only restricts facets, except the facet with identical path expression as this filter.
   *
   *  Therefore, by combining two filters in modes RESULTS and FACETS, you can achieve
   *  the typical faceting behavior where results and all facets except the one being selected are recalculated.
   *  This is exactly what the multi select facets are doing. */
  FACETS
}
