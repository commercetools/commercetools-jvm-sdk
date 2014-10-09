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

 <h3>Predicates</h3>

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


 */
public class QueryDocumentation {
    private QueryDocumentation() {
    }
}
