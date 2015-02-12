package io.sphere.sdk.meta;

import io.sphere.sdk.models.Base;

/** Products can be retrieved using full-text search, filtering, faceting and sorting functionality combined.


 <p>The {@link io.sphere.sdk.meta.QueryDocumentation Query API} lets you search things for their
 field values but does not provide full-text search for multiple fields.</p>
 <p>The search endpoints provide text analysis, full-text search over multiple fields and faceting.
 They are supposed to be faster than the query endpoints with the price of eventual consistency.
 So for example if you change a product name
 it will take some seconds to propagate this change to the search index.</p>

 <p>The following examples are based on the search for products.
 Notice that the result list does NOT contain elements of the type {@link io.sphere.sdk.products.Product}, but
 elements of the type {@link io.sphere.sdk.products.ProductProjection}.
 As a result the class to create a search request for products is called
 {@link io.sphere.sdk.products.search.ProductProjectionSearch} and not {@code ProductSearch}.</p>


<h3 id=full-text-search>Full Text Search</h3>

 <p>With {@link io.sphere.sdk.products.search.ProductProjectionSearch} you can perform a full-text search for a specific language. On the <a href="http://dev.sphere.io/http-api-projects-products-search.html#search-text">Full-Text Search</a> documentation page you can explore which fields are included for the search and other additional information.</p>

 <p>The following example searches for all products containing the word "shoe" in English:</p>

 {@include.example io.sphere.sdk.products.ProductProjectionSearchIntegrationTest#searchByTextInACertainLanguage()}


<h3 id=pagination>Pagination</h3>

 <p>Use {@link io.sphere.sdk.search.SearchDsl#withOffset(long)} and {@link io.sphere.sdk.search.SearchDsl#withLimit(long)} for pagination. An extended explanation about how pagination works in SPHERE.IO can be found in {@link io.sphere.sdk.meta.QueryDocumentation}.</p>

 <p>The following request skips the first 50 products and limits the result set to only 25 products:</p>

 {@include.example io.sphere.sdk.products.ProductProjectionSearchIntegrationTest#paginationExample()}


<h3 id=sort>Sorting</h3>

 <p>Any attribute you can sort by, allow an ascending and descending sort direction. On the <a href="http://dev.sphere.io/http-api-projects-products-search.html#search-sorting">Sorting</a> documentation page you can explore for which fields you can sort for.</p>

 <p>As an example, the next code requests all products sorted by the English name in an ascending direction:</p>

 {@include.example io.sphere.sdk.products.ProductProjectionSearchIntegrationTest#simpleSortExample()}

 <p>When sorting on product custom attributes, you can also choose which should be the selected variant used for sorting. By default the values are sorted internally through variants, selecting the variant that best matches the sorting direction. This behaviour can be easily inverted, as explained in the <a href="http://dev.sphere.io/http-api-projects-products-search.html#search-sorting-attribute">Sorting by Attributes</a> documentation page.</p>

 <p>In the following example the products are sorted by width in an ascending direction, but sorting on the maximum width value within the product variants:</p>

 {@include.example io.sphere.sdk.products.ProductProjectionSearchIntegrationTest#extendedSortExample()}

 <p>Alternatively, there is the possibility to provide directly the sort request, even though this method is unsafe and therefore not recommended.</p>

 <p>Here is the equivalent code for the previous width sorting:</p>

 {@include.example io.sphere.sdk.products.ProductProjectionSearchIntegrationTest#unsafeSortExample()}



 <h3 id=filters-and-facets>Filters and Facets</h3>
<h4 id=filters>Filters</h4>
 <p>A presentation of filters and facets can be found <a href="http://slides.com/lauraluiz/filters-and-facets">here</a>.</p>

 <img src="{@docRoot}/documentation-resources/images/search/search-phases.png" alt="Phases for filters and facets">

<p>There are three types of filters:</p>


 <dl>
    <dt>filter query</dt>
    <dd>This parameter applies a filter to the query results before facets have been calculated. Filter in this scope does influence facet counts. If facets are not used, this scope should be preferred over filter results.
 Use {@link io.sphere.sdk.search.SearchDsl#withFilterQueries(java.util.List)}.</dd>

    <dt>filter results (in HTTP API just "filter")</dt>
    <dd>This parameter applies a filter to the query results after facets have been calculated. Filter in this scope doesn't influence facet counts.
 Use {@link io.sphere.sdk.search.SearchDsl#withFilterResults(java.util.List)}. </dd>

    <dt>filter facets</dt>
    <dd>This parameter applies a filter to all facet calculations (but not query results), except for those facets that operate on the exact same field as the filter. This behavior in combination with the filter scope enables multi-select faceting.
 Use {@link io.sphere.sdk.search.SearchDsl#withFilterFacets(java.util.List)}.</dd>
 </dl>



 <table class="doc-table" summary="table which shows which filter is applied to which phase">
     <tr> <th>&nbsp;</th> <th>filters results</th> <th>filters facets</th> </tr>
     <tr><td>filter query </td><td>yes</td> <td>yes</td> </tr>
     <tr><td>filter results</td><td>yes</td> <td>no</td> </tr>
     <tr><td>filter facet</td><td>no</td> <td>yes</td> </tr>
     <tr><td>using no filter</td><td>no</td> <td>no</td> </tr>
 </table>
<h4 id=facets>Facets</h4>

 For faceted search, results can be of {@link io.sphere.sdk.search.TermFacetResult} or {@link io.sphere.sdk.search.RangeFacetResult}:

 {@include.example io.sphere.sdk.products.ProductProjectionSearchIntegrationTest#responseContainsRangeFacetsForAttributes()}

 {@include.example io.sphere.sdk.products.ProductProjectionSearchIntegrationTest#responseContainsTermFacetsForAttributes()}

 <p>Consult the HTTP API documentation for <a href="http://dev.sphere.io/http-api-projects-products-search.html#search-filters">filters</a> and <a href="http://dev.sphere.io/http-api-projects-products-search.html#search-facets">facets</a> for more information.</p>


 */
public final class ProductSearchDocumentation extends Base {
    private ProductSearchDocumentation() {
    }
}
