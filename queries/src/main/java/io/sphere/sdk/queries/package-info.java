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
 * <p>First, you need to specify a query, example:</p>
 *
 * {@include.example io.sphere.sdk.queries.QueryDemo#formulatingAQuery()}
 *
 * <p>Second, you use the sphere client to execute a query request, example:</p>
 *
 * {@include.example io.sphere.sdk.queries.QueryDemo#executeQuery()}
 *
 * <p>In the last step you map the promise, for example to a Play response, example:</p>
 *
 * {@include.example io.sphere.sdk.queries.QueryDemo#mapResult()}
 *
 * TODO explain pagination
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
 * <h4 id="create-query-with-predicates">Create a query with predicate API</h4>
 * TODO sorting, limit, skip, may can be mixed with others
 * <h4 id="create-query-as-class">Create query with parameterized constructor</h4>
 * <h4 id="create-hard-coded-query">Create a hard coded query</h4>
 */
package io.sphere.sdk.queries;