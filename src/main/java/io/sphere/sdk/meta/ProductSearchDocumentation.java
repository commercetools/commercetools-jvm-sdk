package io.sphere.sdk.meta;

import io.sphere.sdk.models.Base;

/** Products can be retrieved using full-text search, filtering, faceting and sorting functionality combined.


 <p>Even though the {@link io.sphere.sdk.meta.QueryDocumentation Query API} lets you query for resources with certain attribute values, its performance is seriously affected by some attributes, specially by custom attributes, when manipulating data (i.e. filtering, sorting). Moreover, some typical operations over a list of resources, such as full-text search, are simply not supported.</p>

 <p>The Search API is specially designed to support those uses cases where the Query API is not powerful enough, by providing not only full-text search, filtering, and sorting, but also statistical analysis of the data with facets.</p>

 <p>The search endpoints are supposed to be faster than the query endpoints, but for the price of eventual consistency. In other words, whenever the name of a product was changed, it will still take some seconds to propagate this change to the search index.</p>

 <p>Currently only products have a search endpoint for {@link io.sphere.sdk.products.ProductProjection} only. Therefore, be aware that the class to create a search request for products is called {@link io.sphere.sdk.products.search.ProductProjectionSearch}.</p>

 <p>The following examples in this document are based on the search for products. The product data defined in the SPHERE.IO platform used for the following code examples are:

 <table border="1" class="doc-table" summary="table that shows the products used for the code examples">
    <tr>
        <th>Product</th>
         <th colspan="2">Product1 "shoe"</th>
         <th colspan="2">Product2 "shirt"</th>
         <th colspan="2">Product3 "dress"</th>
     </tr>
     <tr align="center">
         <th>Variant</th>
         <th>1</th>
         <th>2</th>
         <th>1</th>
         <th>2</th>
         <th>1</th>
         <th>2</th>
     </tr>
    <tr align="center">
         <th>Color</th>
         <td>blue</td>
         <td></td>
         <td>red</td>
         <td></td>
         <td>blue</td>
         <td></td>
    </tr>
    <tr align="center">
         <th>Size</th>
         <td>38</td>
         <td>46</td>
         <td>36</td>
         <td>44</td>
         <td>40</td>
         <td>42</td>
     </tr>
 </table>


<h3 id=full-text-search>Full Text Search</h3>

 <p>With {@link io.sphere.sdk.products.search.ProductProjectionSearch} you can perform a full-text search for a specific language. On the <a href="http://dev.sphere.io/http-api-projects-products-search.html#search-text">Full-Text Search</a> documentation page you can explore which fields are included for the search and other additional information.</p>

 <p>The following example searches for all products containing the word "shoe" in English:</p>

 {@include.example io.sphere.sdk.products.ProductProjectionSearchIntegrationTest#searchByTextInACertainLanguage()}



<h3 id=pagination>Pagination</h3>

 <p>Use {@link io.sphere.sdk.search.SearchDsl#withOffset(long)} and {@link io.sphere.sdk.search.SearchDsl#withLimit(long)} for pagination. An extended explanation about how pagination works in SPHERE.IO can be found in {@link io.sphere.sdk.meta.QueryDocumentation}.</p>

 <p>The following request skips the first 50 products and limits the result set to only 25 products:</p>

 {@include.example io.sphere.sdk.products.ProductProjectionSearchIntegrationTest#paginationExample()}



<h3 id=sort>Sorting</h3>

 <p>Any attribute you can sort by, allows both sort directions, ascending and descending. On the <a href="http://dev.sphere.io/http-api-projects-products-search.html#search-sorting">Sorting</a> documentation page you can explore for which fields you can sort for.</p>

 <p>The code sample below shows a request for all products which will be sorted by size in an ascending direction:</p>

 {@include.example io.sphere.sdk.products.ProductProjectionSearchIntegrationTest#sortByAttributeAscending()}

 <p>When sorting on product custom attributes, you can also choose which variant should be used for sorting. By default, the values are sorted through variants internally, selecting the best-matching variant according to the sorting direction. This behaviour can easily be inverted, as explained in the <a href="http://dev.sphere.io/http-api-projects-products-search.html#search-sorting-attribute">Sorting by Attributes</a> documentation page.</p>

 <p>In the following example the products are sorted by size in an ascending direction, but sorted by size with the highest value within each product variants instead:</p>

 {@include.example io.sphere.sdk.products.ProductProjectionSearchIntegrationTest#sortWithAdditionalParameterByAttributeAscending()}

 <p>Alternatively, you can provide the sort request directly, even though this method is unsafe and therefore not recommended.</p>

 <p>Here is the alternative code equivalent to the previous sorting by size:</p>

 {@include.example io.sphere.sdk.products.ProductProjectionSearchIntegrationTest#sortWithSimpleExpression()}



<h3 id=filters>Filters</h3>

 <h4 id=filter-types>Types</h4>

 <p>When searching for products, there are three stages of the process where filters can be applied, as shown in the following drawing:</p>

 <img class="theme-img" src="{@docRoot}/documentation-resources/images/search/search-phases.png" alt="Filter types">
 <p class="image-caption">Filter types</p>

 <dl>
    <dt>Filter Query: {@link io.sphere.sdk.search.SearchDsl#withFilterQuery(java.util.List)}.</dt>
    <dd>This parameter allows to filter products BEFORE facets have been calculated, therefore this scope affects both results and facets.</dd>

    <dt>Filter Results (in the HTTP API it is called just "filter"): {@link io.sphere.sdk.search.SearchDsl#withFilterResults(java.util.List)}</dt>
    <dd>This parameter allows to filter products AFTER facets have been calculated, therefore this scope affects the results only. Using this filter only makes sense when used together with facets, otherwise Filter Query should be preferred.</dd>

    <dt>Filter Facets: {@link io.sphere.sdk.search.SearchDsl#withFilterFacets(java.util.List)}</dt>
    <dd>This parameter allows to filter those products used for facets calculation only, without affecting the results whatsoever. All facet calculations are affected except for those facets operating on the same field as the filter, enabling multi-select faceting when combined with Filter Results.</dd>
 </dl>

  <table border="1" class="doc-table" summary="table that shows which filter is applied to which output">
    <tr>
        <th>&nbsp;</th>
        <th>Does it filter results?</th>
        <th>Does it filter facets?</th>
    </tr>
    <tr align="center">
        <th>Filter Query</th>
        <td>Yes</td>
        <td>Yes</td>
    </tr>
    <tr align="center">
        <th>Filter Results</th>
        <td>Yes</td>
        <td>No</td>
    </tr>
    <tr align="center">
        <th>Filter Facets</th>
        <td>No</td>
        <td>Yes</td>
    </tr>
    <tr align="center">
        <th>No filter</th>
        <td>No</td>
        <td>No</td>
    </tr>
 </table>

 <p>For further explanation, some diagrams regarding the filter mechanism can be found in the <a href="http://slides.com/lauraluiz/filters-and-facets">Filters and Facets</a> presentation.</p>

 <h4 id=filter-expressions>Expressions</h4>

 <p>You can easily build filter expressions with the {@link io.sphere.sdk.products.search.ExperimentalProductProjectionSearchModel} class. For more details, check the HTTP API documentation for <a href="http://dev.sphere.io/http-api-projects-products-search.html#search-filters">Filters</a>.</p>

 <p>In the following example only the products with red color are returned:</p>

 {@include.example io.sphere.sdk.products.ProductProjectionSearchIntegrationTest#filtersByTerm()}

 <p>Besides filtering by terms, you can also filter by a range of values, as shown in the following code.</p>

 <p>Here we are requesting only those products with at least one variant having the size attribute greater than or equals to 44:</p>

 {@include.example io.sphere.sdk.products.ProductProjectionSearchIntegrationTest#filtersByRange()}

 <p>There is also the possibility to provide the filter expression directly, although it is unsafe and thus not recommended.</p>

 <p>In the following example the same filter by size is requested as previously, but now in this way:</p>

 {@include.example io.sphere.sdk.products.ProductProjectionSearchIntegrationTest#simpleFilterByRange()}



 <h3 id=facets>Facets</h3>

 <h4 id=facet-expressions>Expressions</h4>

 <p>Facets calculate statistical counts based on the values associated with a product attribute. Building facet expressions is very easy thanks to the {@link io.sphere.sdk.products.search.ExperimentalProductProjectionSearchModel} class. You can consult the <a href="http://dev.sphere.io/http-api-projects-products-search.html#search-facets">Facets</a> HTTP API documentation for more information.</p>

 <p>There are three types of faceted search: {@link io.sphere.sdk.search.TermFacetResult}, {@link io.sphere.sdk.search.RangeFacetResult} and {@link io.sphere.sdk.search.FilteredFacetResult}. Next are presented example codes of each type to better illustrate how these facet types work.</p>

 <p>The Term Facet result obtained with the following code contains all different size values found in the products, along with the statistical count of the amount of product variants with that value associated:</p>

 {@include.example io.sphere.sdk.products.ProductProjectionSearchIntegrationTest#termFacetsAreParsed()}

 <p>In contrast, the Filtered Facet result contains the statistical count for a single value specified in the request, in this case the amount of variants with blue color:</p>

 {@include.example io.sphere.sdk.products.ProductProjectionSearchIntegrationTest#filteredFacetsAreParsed()}

 <p>You can also request statistics (i.e. count, minimum and maximum values, as well as the sum and arithmetic mean) about a range of values, like it is done with size in the following example within the range [0, +∞):</p>

 {@include.example io.sphere.sdk.products.ProductProjectionSearchIntegrationTest#rangeFacetsAreParsed()}

 <p>There is also the possibility to provide the facet expression directly, although it is unsafe and thus not recommended.</p>

 <p>In the following code the same request as in the Term Facet example is reproduced, but providing a facet expression now instead:</p>

 {@include.example io.sphere.sdk.products.ProductProjectionSearchIntegrationTest#simpleFacetsAreParsed()}

 <h4 id=facet-alias>Alias</h4>

 <p>Additionally, facets allow to specify an alias which will then replace the attribute path in the result. This functionality allows to calculate different types of facets on the same attribute. There is an extended explanation with examples in the API documentation for <a href="http://dev.sphere.io/http-api-projects-products-search.html#search-facets">Facets</a>.</p>

 <p>In order to use it, there is a method that allows to define the desired alias:</p>

 {@include.example io.sphere.sdk.products.ProductProjectionSearchIntegrationTest#filteredFacetsSupportsAlias()}

 */
public final class ProductSearchDocumentation extends Base {
    private ProductSearchDocumentation() {
    }
}
