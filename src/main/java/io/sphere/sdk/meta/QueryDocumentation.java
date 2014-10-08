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

 <p>A common use case to query a product is to query it by slug. For this and other most common use cases exist helper methods as shown in the next example.</p>

 {@include.example io.sphere.sdk.meta.QueryDocumentationTest#queryBySlug()}

 You may have noticed that the type of the query is not {@link io.sphere.sdk.products.queries.ProductQuery} anymore but {@link io.sphere.sdk.queries.QueryDsl} which does not contain the method {@link io.sphere.sdk.products.queries.ProductQuery#bySlug(io.sphere.sdk.products.ProductProjectionType, java.util.Locale, String)}.
 That is due to the implementation of the domain specific language and still enables you to configure pagination and sorting.

 <p>Important to know is that the {@link io.sphere.sdk.queries.QueryDsl} uses immutable objects, so the call of {@link io.sphere.sdk.products.queries.ProductQuery#bySlug(io.sphere.sdk.products.ProductProjectionType, java.util.Locale, String)} does not change the internal state of the {@link io.sphere.sdk.products.queries.ProductQuery} but creates a new {@link io.sphere.sdk.queries.QueryDsl} object with the selected predicate.</p>

<p>For more advanced queries you have to use the {@link io.sphere.sdk.queries.Predicate Predicate API}. For example querying for some names you can use:</p>

 {@include.example io.sphere.sdk.meta.QueryDocumentationTest#queryByNames()}

 <p>The Predicate API looks verbose, but it is it's goal to prevent typos and make it via the IDE discoverable for which attributes can be queried.</p>

 You can still create predicates from strings, but you need to escape characters.

 {@include.example io.sphere.sdk.meta.QueryDocumentationTest#testX()}







 */
public class QueryDocumentation {
    private QueryDocumentation() {
    }
}
