package io.sphere.sdk.meta;

/**
 <p>The Query API is for reading specific resources from SPHERE.IO.
 The resources can be sorted and fetched in batches.</p>

 <p>The successful execution of a {@link io.sphere.sdk.queries.Query} results in {@link io.sphere.sdk.queries.PagedQueryResult} of a SPHERE.IO resource or view.
 While the {@link io.sphere.sdk.queries.Query} interface just contains information to execute a query,
 the interface {@link io.sphere.sdk.queries.QueryDsl} provides also a domain specific language to tune a query.</p>

 <p>For most of the SPHERE.IO resources classes (in a package queries) exist to support you formulating valid queries.</p>

 <p>The following snipped creates a query which selects all products not meaningful ordered.</p>

 {@include.example io.sphere.sdk.meta.QueryDocumentationTest#queryForAllDemo()}

 <h3 id=predicates>Predicates</h3>

 <h4>Helper methods for common use cases</h4>

 <p>A common use case to query a product is to query it by slug. For this and other most common use cases exist helper methods as shown in the next example.</p>

 {@include.example io.sphere.sdk.meta.QueryDocumentationTest#queryBySlug()}

 You may have noticed that the type of the query is not {@link io.sphere.sdk.products.queries.ProductQuery} anymore but {@link io.sphere.sdk.queries.QueryDsl} which does not contain the method {@link io.sphere.sdk.products.queries.ProductQuery#bySlug(io.sphere.sdk.products.ProductProjectionType, java.util.Locale, String)}.
 That is due to the implementation of the domain specific language and still enables you to configure pagination and sorting.

 <p>Important to know is that the {@link io.sphere.sdk.queries.QueryDsl} uses immutable objects, so the call of {@link io.sphere.sdk.products.queries.ProductQuery#bySlug(io.sphere.sdk.products.ProductProjectionType, java.util.Locale, String)} does not change the internal state of the {@link io.sphere.sdk.products.queries.ProductQuery} but creates a new {@link io.sphere.sdk.queries.QueryDsl} object with the selected predicate.</p>

 <h4>Self constructed predicates for special cases</h4>

<p>For more advanced queries you have to use the {@link io.sphere.sdk.queries.Predicate Predicate API}. For example querying for some names you can use:</p>

 {@include.example io.sphere.sdk.meta.QueryDocumentationTest#queryByNames()}

 <p>The Predicate API looks verbose, but it is it's goal to prevent typos and make it via the IDE discoverable for which attributes can be queried.</p>

 You can still create predicates from strings, but you need to escape characters.

 {@include.example io.sphere.sdk.meta.QueryDocumentationTest#testX()}

 <p>For creating predicates for resource &lt;RESOURCE&gt; there is a method {@code model()} in a class &lt;RESOURCE&gt;Query.</p>

 <h4>Connecting predicates</h4>

 <p>For connecting predicates use {@link io.sphere.sdk.queries.Predicate#and(io.sphere.sdk.queries.Predicate)} and {@link io.sphere.sdk.queries.Predicate#or(io.sphere.sdk.queries.Predicate)}.</p>


 <p>The following example shows a query for a product where either the name is "foo" <strong>{@code or}</strong> the id is "bar".</p>

 {@include.example io.sphere.sdk.meta.QueryDocumentationTest#predicateOrExample()}

 <p>To query for two conditions use <strong>{@code and}</strong>. The following example queries for the name foo and the membership in a category with id "cat1".</p>

 {@include.example io.sphere.sdk.meta.QueryDocumentationTest#predicateAndExample()}

 <p>Since the previous queries have a common path you can formulate a combined query with <strong>{@code where}</strong>, but this is not possible for every entity.</p>

 {@include.example io.sphere.sdk.meta.QueryDocumentationTest#predicateAndWithWhereExample()}


 <h4>Negate predicates</h4>

 It is not possible to negate complete predicates, but it is possible on the field level. So for example the next query is for products where the name is not exactly "foo".

 {@include.example io.sphere.sdk.meta.QueryDocumentationTest#predicateNotExample()}

 <h3 id=sorting>Sorting</h3>

 For paginated queries and consistent user visualization it makes sense to sort the search results. If you don't specify the sorting, results can appear in a random order.
 <p>Some default implementations sort by ID by default so even if you forget to specify any sorting options, the order of the results is constant.</p>

 <p>To specify the sorting use {@link io.sphere.sdk.queries.QueryDsl#withSort(java.util.List)}.</p>
 The following example sorts by name in the English locale.

 {@include.example io.sphere.sdk.meta.QueryDocumentationTest#sortByName()}

 To specify a tested and type-safe sort expression you can traverse the query model tree like for the predicates, but instead of providing values use the the {@code sort()} method.

 <p>If the SDK lacks of a method to create the sort expression, you can still provide it via a string:</p>

 {@include.example io.sphere.sdk.meta.QueryDocumentationTest#sortCreationByString()}

 <h3 id=pagination>Pagination</h3>

 <p>Assumption: in a product query all products are sorted by ID by default.</p>

 A query might produce more results you want consume or SPHERE.IO let you consume. At the time of
 writing you can only fetch up to 500 objects at once.

 <p>Imagine you have 15 products:</p>

 <pre> 00 01 02 03 04 05 06 07 08 09 10 11 12 13 14 </pre>

 <p>And a query:</p>

 {@include.example io.sphere.sdk.meta.QueryDocumentationTest#queryAllExampleInPaginationContext()}

 <p>If you do not specify a limitation how many resources should be fetched with one query
 with {@link io.sphere.sdk.queries.QueryDsl#withLimit(long)} they (the 15) will all be loaded.</p>

 <p>If you specify a limit of 4 the query, a query will only load the first 4 products:</p>
 {@include.example io.sphere.sdk.meta.QueryDocumentationTest#limitProductQueryTo4()}
<pre>|00 01 02 03|</pre>


 To load the next 4 products you need to define an offset with {@link io.sphere.sdk.queries.QueryDsl#withOffset(long)}.
 <p>If you specify a limit of 4 the query, a query will only load the first 4 products:</p>
 {@include.example io.sphere.sdk.meta.QueryDocumentationTest#limitProductQueryTo4PlusOffset4()}
 <pre>|04 05 06 07|</pre>

 To fetch the products with the ID 08 to 11 you need an offset parameter of 8.
 <p>If you use an offset of 2 and a limit of 4 you will fetch products 02, 03, 04, 05.</p>

 <pre>  00 01|02 03 04 05|06 07 08 09 10 11 12 13 14
 skip 2|limit of 4 | not fetched</pre>

 */
public class QueryDocumentation {
    private QueryDocumentation() {
    }
}
