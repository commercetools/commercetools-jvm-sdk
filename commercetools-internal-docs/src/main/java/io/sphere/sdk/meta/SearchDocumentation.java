package io.sphere.sdk.meta;

import io.sphere.sdk.models.Base;

/** Products can be retrieved using full-text search, filtering, faceting and sorting functionality combined.


 <p>Even though the {@link io.sphere.sdk.meta.QueryDocumentation Query API} lets you query for resources with certain attribute values, its performance is seriously affected by some attributes, specially by custom attributes, when manipulating data (i.e. filtering, sorting). Moreover, some typical operations over a list of resources, such as full-text search and facets, are simply not supported.</p>

 <p>The Search API is specially designed to support those uses cases where the Query API is not powerful enough, by providing not only full-text search, filtering, and sorting, but also statistical analysis of the data with facets.</p>

 <p>The search endpoints are supposed to be faster than the query endpoints, but for the price of eventual consistency. In other words, whenever the name of a product was changed, it will still take some seconds to propagate this change to the search index.</p>

 <p>Currently only products have a search endpoint for {@link io.sphere.sdk.products.ProductProjection} only. Therefore, be aware that the class to create a search request for products is called {@link io.sphere.sdk.products.search.ProductProjectionSearch}.</p>

 <p>The following examples in this document are based on the search for products. The product data defined in the platform used for the following code examples are:

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

 <p>With {@link io.sphere.sdk.products.search.ProductProjectionSearch} you can perform a full-text search for a specific language. On the <a href="https://docs.commercetools.com/http-api-projects-products-search.html#search-text">Full-Text Search</a> documentation page you can explore which fields are included for the search and other additional information.</p>

 <p>The following example uses {@link io.sphere.sdk.search.SearchDsl#withText(java.util.Locale, String)} to search for all products containing the word "shoe" in English:</p>

 {@include.example io.sphere.sdk.products.search.ProductProjectionSearchMainIntegrationTest#searchByTextInACertainLanguage()}



<h3 id=pagination>Pagination</h3>

 <p>Use {@link io.sphere.sdk.search.SearchDsl#withOffset(Long)} and {@link io.sphere.sdk.search.SearchDsl#withLimit(Long)} for pagination. An extended explanation about how pagination works in the platform can be found in {@link io.sphere.sdk.meta.QueryDocumentation}.</p>

 <p>The following request skips the first 50 products and limits the result set to only 25 products:</p>

 {@include.example io.sphere.sdk.products.search.ProductProjectionSearchMainIntegrationTest#paginationExample()}



<h3 id=sorting>Sorting</h3>

 <p>Any attribute you can sort by, allows both sort directions, ascending and descending. On the <a href="https://docs.commercetools.com/http-api-projects-products-search.html#search-sorting">Sorting</a> documentation page you can explore for which fields you can sort for. Use {@link io.sphere.sdk.products.search.ProductProjectionSortSearchModel} class to build sort expressions easily.</p>

 <p>The code sample below shows a request for all products which will be sorted by size in an ascending direction:</p>

 {@include.example io.sphere.sdk.products.search.ProductProjectionSearchSortIntegrationTest#sortByAttributeAscending()}

 <p>When sorting on multi-valued attributes, such as attributes from product variants, you can also choose which value should be used for sorting. By default, the value selected is the best-matching according to the sorting direction, or in other words, the minimum value for ascending and the maximum value for descending sort. This behaviour can be easily inverted, as explained in the <a href="https://docs.commercetools.com/http-api-projects-products-search.html#search-sorting-attribute">Sorting by Attributes</a> documentation page.</p>

 <p>In the following example the products are sorted by size in an ascending direction, but sorted using the highest size value within each product variants instead:</p>

 {@include.example io.sphere.sdk.products.search.ProductProjectionSearchSortIntegrationTest#sortWithAdditionalParameterByAttributeAscending()}

 <p>Alternatively, you can provide the sort request directly, even though this method is unsafe and therefore not recommended.</p>

 <p>Here is the alternative code equivalent to the previous sorting by size:</p>

 {@include.example io.sphere.sdk.products.search.ProductProjectionSearchSortIntegrationTest#sortWithSimpleExpression()}

 <p>Providing a list of sort expressions has the effect of multi-sorting, as it is demonstrated in the following example:</p>

 {@include.example io.sphere.sdk.products.search.ProductProjectionSearchSortIntegrationTest#sortByMultipleAttributes()}




 <h3 id=faceting>Faceting</h3>

 <h4 id=facet-expressions>Expressions</h4>

 <p>Facets calculate statistical counts based on the values associated with an attribute. Building facet expressions is very easy thanks to the {@link io.sphere.sdk.products.search.ProductProjectionFacetSearchModel} class. You can consult the <a href="https://docs.commercetools.com/http-api-projects-products-search.html#search-facets">Facets</a> HTTP API documentation for more information.</p>

 <p>There are three types of facets: {@link io.sphere.sdk.search.TermFacetResult}, {@link io.sphere.sdk.search.RangeFacetResult} and {@link io.sphere.sdk.search.FilteredFacetResult}. Next are presented example codes of each type to better illustrate how these facet types work.</p>

 <p>The Term Facet result obtained with the following code contains all different size values found in the products, along with the statistical count of the amount of product variants with that value associated:</p>

 {@include.example io.sphere.sdk.products.search.ProductProjectionSearchFacetsIntegrationTest#termFacetsAreParsed()}

 <p>In contrast, the Filtered Facet result contains the statistical count for some certain values specified in the request, in this case the amount of variants with blue color:</p>

 {@include.example io.sphere.sdk.products.search.ProductProjectionSearchFacetsIntegrationTest#filteredFacetsAreParsed()}

 <p>You can also request statistics (i.e. count, minimum and maximum values, as well as the sum and arithmetic mean) about a range of values, like it is done with size in the following example within the range [0, +∞):</p>

 {@include.example io.sphere.sdk.products.search.ProductProjectionSearchFacetsIntegrationTest#rangeFacetsAreParsed()}

 <p>There is also the possibility to provide the facet expression directly, although it is unsafe and thus not recommended.</p>

 <p>In the following code the same request as in the Term Facet example is reproduced, but providing a facet expression now instead:</p>

 {@include.example io.sphere.sdk.products.search.ProductProjectionSearchFacetsIntegrationTest#simpleFacetsAreParsed()}

 <h4 id=facet-alias>Alias</h4>

 <p>Additionally, facets allow to specify an alias which will then replace the attribute path in the result. This functionality allows to calculate different types of facets on the same attribute. There is an extended explanation with examples in the API documentation for <a href="https://docs.commercetools.com/http-api-projects-products-search.html#search-facets">Facets</a>.</p>

 <p>In order to use it, the SDK offers a method that allows to define the desired alias:</p>

 {@include.example io.sphere.sdk.products.search.ProductProjectionSearchFacetsIntegrationTest#termFacetsSupportsAlias()}




 <h3 id=filtering>Filtering</h3>

 <h4 id=filter-types>Types</h4>

 <p>When searching for products, there are three stages of the process where filters can be applied, as shown in the following drawing:</p>

 <img class="theme-img" src="{@docRoot}/resources/images/search/search-phases.png" alt="Filter types">
 <p class="image-caption">Filter types</p>

 <dl>
    <dt>Filter Query: {@link io.sphere.sdk.search.FilterDsl#withQueryFilters(java.util.List)}.</dt>
    <dd>This parameter allows to filter products BEFORE facets have been calculated, therefore this scope affects both results and facets.</dd>

    <dt>Filter Results (in the HTTP API it is called just "filter"): {@link io.sphere.sdk.search.FilterDsl#withResultFilters(java.util.List)}</dt>
    <dd>This parameter allows to filter products AFTER facets have been calculated, therefore this scope affects the results only. Using this filter only makes sense when used together with facets, otherwise Filter Query should be preferred for performance reasons.</dd>

    <dt>Filter Facets: {@link io.sphere.sdk.search.FilterDsl#withFacetFilters(java.util.List)}</dt>
    <dd>This parameter allows to filter those products used for facets calculation only, without affecting the results whatsoever. All facet calculations are affected except for those facets operating on the same field as the filter, enabling faceted search when combined with Filter Results.</dd>
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

 <p>For further explanation, some diagrams regarding the filter mechanism can be found in the <a href="https://slides.com/lauraluiz/filters-and-facets">Filters and Facets</a> presentation.</p>

 <h4 id=filter-expressions>Expressions</h4>

 <p>You can easily build filter expressions with the {@link io.sphere.sdk.products.search.ProductProjectionFilterSearchModel} class. For more details, check the HTTP API documentation for <a href="https://docs.commercetools.com/http-api-projects-products-search.html#search-filters">Filters</a>.</p>

 <p>In the following example only the products with red color are returned:</p>

 {@include.example io.sphere.sdk.products.search.ProductProjectionSearchFiltersIntegrationTest#filtersByTerm()}

 <p>Besides filtering by terms, you can also filter by a range of values, as shown in the following code.</p>

 <p>Here we are requesting only those products with at least one variant having the size attribute greater than or equals to 44:</p>

 {@include.example io.sphere.sdk.products.search.ProductProjectionSearchFiltersIntegrationTest#filtersByRange()}

 <p>There is also the possibility to provide the filter expression directly, although it is unsafe and thus not recommended.</p>

 <p>In the following example the same filter by size is requested as previously, but now in this way:</p>

 {@include.example io.sphere.sdk.products.search.ProductProjectionSearchFiltersIntegrationTest#simpleFilterByRange()}


 <h3 id=model>Product Projection Search Model</h3>

 <p>The Search Model for products is currently in an experimental status, so please be aware it will probably suffer breaking changes in the future.</p>

 <p>The {@link io.sphere.sdk.products.search.ProductProjectionSearchModel} class offers a domain-specific language (DSL) to build expressions accepted by the search endpoint, which can be otherwise a bit complex to write. In particular, this DSL allows you to build expressions for <a href="#sorting">sorting</a>, <a href="#filtering">filtering</a> and <a href="#faceting">faceting</a>, as it is demonstrated in the corresponding sections.</p>

 <p>In order to build an expression, first you will be required to select the action to apply (i.e. {@link io.sphere.sdk.products.search.ProductProjectionSearchModel#filter}, {@link io.sphere.sdk.products.search.ProductProjectionSearchModel#facet} and {@link io.sphere.sdk.products.search.ProductProjectionSearchModel#sort}).</p>

 <p>Then you need to build the path of the attribute you want to apply that action to, for example {@link io.sphere.sdk.products.search.ProductProjectionFilterSearchModel#lastModifiedAt} represents the {@code lastModifiedAt} attribute on a product. In the case of custom attributes, you also need to provide the attribute name and select the primary type of the attribute.</p>

 <p>Finally, you need to indicate the criteria used for the desired operation. For example you would use {@link io.sphere.sdk.search.model.TermFilterSearchModel#isIn} if you want to filter by any of the given values.</p>

 <p>Notice that even though the Search Model expects values of a certain type based on the attribute, you can force it to accept simple strings as shown in the following example:</p>

 {@include.example io.sphere.sdk.products.search.ProductProjectionSearchFiltersIntegrationTest#filterByValueAsString}

 <h4 id=faceted-search>Faceted Search</h4>

 <p>A faceted search is the most typical application of facets and filters when browsing products, which consists of using some of the facet result values as filter parameters. This enables the user to see all possible attribute values of the current product list (using facets) and search by these exact values (using filters).</p>

 <p>This behaviour is achieved by using on the same attribute a {@code Facet} for all terms or ranges, together with a {@code Filter Facets} and {@code Filter Results}, both with the same filter parameters.</p>

 <p>The {@link io.sphere.sdk.products.search.ProductProjectionSearchModel} simplifies all this process by allowing you to build faceted search expressions directly, just providing the required filter parameters.</p>

 <p>The following example demonstrates how to perform a faceted search with the model, involving two different attributes:</p>

 {@include.example io.sphere.sdk.products.search.ProductProjectionSearchFacetedSearchIntegrationTest#facetedSearchExample}

 <p>You can see now how the same request is performed without the faceted search model:</p>

 {@include.example io.sphere.sdk.products.search.ProductProjectionSearchFacetedSearchIntegrationTest#facetedSearchVerboseExample}

 */
public final class SearchDocumentation extends Base {
    private SearchDocumentation() {
    }
}
