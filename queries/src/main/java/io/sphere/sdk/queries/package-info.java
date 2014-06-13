/**
 *
 * <p>The query API enables to load objects from the SPHERE.IO backend based on predicates with pagination.</p>
 *
 * <ol>
 *     <li><a href="#working-with-queries">Working with queries</a></li>
 *     <li><a href="#creating-queries">Creating queries</a></li>
 * </ol>
 *
 * <h3 id="working-with-queries">Working with queries</h3>
 *
 * <h4>Flow</h4>
 *
 * <p>First, you need to specify a query, example:</p>
 *
 * {@include.example io.sphere.sdk.queries.QueryDemo#formulatingAQuery()}
 *
 * <p>Second, you use the sphere client to execute a query request, example:</p>
 *
 * {@include.example io.sphere.sdk.queries.QueryDemo#executeQuery()}
 *
 * <p>In the last step you map the promise, for example to a Play response:</p>
 *
 * {@include.example io.sphere.sdk.queries.QueryDemo#mapResult()}
 *
 * <h4>Pagination</h4>
 *
 * TODO sorting, limit, skip on request side
 * TODO PagedQueryResult (link)
 *
 * <h3 id="creating-queries">Creating queries</h3>
 *
 *  There are several ways to create a query:
 *
 *  <ol>
 *      <li><a href="#create-query-with-model-companion-class">Create a query with helper methods from a model companion class</a></li>
 *      <li><a href="#create-query-with-predicates">Create a query with predicate API</a></li>
 *      <li><a href="#create-query-as-class">Create query with parameterized constructor</a></li>
 *      <li><a href="#create-hard-coded-query">Create a hard coded query</a></li>
 *  </ol>
 *
 * <h4 id="create-query-with-model-companion-class">Create a query with helper methods from a model companion class</h4>
 *
 * <p>For some model interfaces exist companion classes (the name of the interface in plural) which provide some default queries, example for categories:</p>
 *
 * {@include.example io.sphere.sdk.queries.QueryDemo#queryFromCompanionHelper()}
 *
 * <h4 id="create-query-with-predicates">Create a query with predicate API</h4>
 *
 * <p>For queryable model interface exists a query model class, e.g., for {@link io.sphere.sdk.categories.Category}
 * exists {@link io.sphere.sdk.categories.CategoryQueryModel}, which contains a DSL to find and specify
 * the queryable parameters.
 * </p>
 *
 * {@include.example io.sphere.sdk.queries.QueryDemo#categoryQueryModel()}
 *
 * <p>The generic code looks verbose, but in the future it enables powerful type-safe queries with IDE discovery even on deep
 * nested data structures like products. (coming soon)</p>
 *
 * The {@link io.sphere.sdk.queries.QueryDslImpl}, used by the query model classes, sorts by default by ID and has no offset
 * or limit specified. The following example shows how to specify sorting, limiting and skipping pages.
 *
 * {@include.example io.sphere.sdk.queries.QueryDemo#withPagination()}
 *
 * The {@link io.sphere.sdk.queries.QueryDsl} is an interface for immutable objects, so if you call {@code withXyz(value)}
 * it returns a new immutable object:
 *
 * {@include.example io.sphere.sdk.queries.QueryDemo#immutableQueryDsl()}
 *
 * As a result you can construct the query for the next page by using the previous query:
 *
 * {@include.example io.sphere.sdk.queries.QueryDemo#nextPage()}
 *
 * <h4 id="create-query-as-class">Create query with parameterized constructor</h4>
 * <h4 id="create-hard-coded-query">Create a hard coded query</h4>
 */
package io.sphere.sdk.queries;