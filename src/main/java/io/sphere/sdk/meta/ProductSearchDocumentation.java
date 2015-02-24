package io.sphere.sdk.meta;

/** Products can be retrieved using full-text search, filtering and faceting functionality combined.


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

 <p>With {@link io.sphere.sdk.products.search.ProductProjectionSearch} you can perform a full-text search for a specific language.
 The following example searches for products containing the word "shoe" in English.</p>

 {@include.example io.sphere.sdk.products.ProductProjectionSearchIntegrationTest#searchByTextInACertainLanguage()}

 <p>On the <a href="http://dev.sphere.io/http-api-projects-products-search.html#search-text">full text search documentation page</a> you can explore which fields are included for the search.</p>

<h3 id=pagination>Pagination and Sorting</h3>

 <p>How pagination works in SPHERE.IO is described in {@link io.sphere.sdk.meta.QueryDocumentation}.</p>

 <p>Use {@link io.sphere.sdk.search.SearchDsl#withOffset(long)}, {@link io.sphere.sdk.search.SearchDsl#withLimit(long)}
 and {@link io.sphere.sdk.search.SearchDsl#withSort(io.sphere.sdk.search.SearchSort)} for pagination. For example
 the following request searches all products with a certain sort expression (not shown), skipping the first 50 products
 and limiting the result set to only 25 products:</p>

 {@include.example io.sphere.sdk.products.ProductProjectionSearchIntegrationTest#paginationExample()}

 <p>On the <a href="http://dev.sphere.io/http-api-projects-products-search.html#search-sorting">the sorting documentation page</a> you can explore for which fields you can sort for.</p>

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

 For faceted search, results can be of {@link io.sphere.sdk.search.TermFacetResult} or {@link io.sphere.sdk.search.RangeFacetResult}.


 <p>Consult the HTTP API documentation for <a href="http://dev.sphere.io/http-api-projects-products-search.html#search-filters">filters</a> and <a href="http://dev.sphere.io/http-api-projects-products-search.html#search-facets">facets</a> for more information.</p>


 */
public final class ProductSearchDocumentation {
    private ProductSearchDocumentation() {
    }
}
