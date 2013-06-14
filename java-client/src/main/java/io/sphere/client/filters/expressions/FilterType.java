package io.sphere.client.filters.expressions;

/** The way a search filter influences facet counts. */
public enum FilterType {
  /** The filter restricts the result set as well as <i>all</i> facets. This is the default. */
  RESULTS_AND_FACETS,
  /** The filter restricts the result set but has no effect on facets. */
  RESULTS,
  /** The filter does not restrict the result set itself.
   *  It only restricts facets, except the facet with identical path expression as this filter. */
  FACETS,
  /** The filter restricts the result set as well as all facets, except facets with identical path expression
   *  as this filter.
   *  This is a good way to achieve standard multiselect faceting behavior, where a single attribute is
   *  used for faceting as well as for filtering. */
  SMART
}
