package io.sphere.client;

import com.google.common.util.concurrent.ListenableFuture;
import io.sphere.client.model.QueryResult;

/** Request that uses a Sphere query API to fetch objects satisfying some conditions. */
public interface QueryRequest<T> {
    /** Executes the request and returns the result. */
    QueryResult<T> fetch();

    /** Executes the request asynchronously and returns a future providing the result. */
    ListenableFuture<QueryResult<T>> fetchAsync();

    /**
     * Sets the predicate used to filter the results for this query request.
     * @param predicate The predicate which is used to filter the results.
     *                  Example: <pre>"name(en=\"myName\")"</pre>.
     **/
    QueryRequest<T> where(String predicate);

    /**
     * Sets the sort expression for sorting the result.
     * @param sort the attribute and the direction to sort with
     *                  Example: <pre>"name.en desc"</pre>.
     **/
    QueryRequest<T> sort(String sort);

    /** Sets the page number for paging through results. Page numbers start at zero. */
    QueryRequest<T> page(int page);

    /** Sets the size of a page for paging through results. When page size is not set, the default of 10 is used. */
    QueryRequest<T> pageSize(int pageSize);

    /** Requests {@linkplain io.sphere.client.model.Reference Reference fields} to be expanded in the returned objects.
     *  Expanded references contain full target objects they link to.
     *
     *  <p>As an illustration, here is how reference expansion looks at the underlying JSON transport level.
     *  Given a customer object:
     *<pre>{@code
     *{
     *  "name": "John Doe"
     *  "customerGroup": {
     *    "typeId": "customer-group",
     *    "id": "7ba61480-6a72-4a2a-a72e-cd39f75a7ef2"
     *  }
     *}}</pre>
     *
     * This is what the result looks like when the path 'customerGroup' has been expanded:
     *
     *<pre>{@code
     *{
     *  "name": "John Doe"
     *  "customerGroup": {
     *    typeId: "customer-group",
     *    id: "7ba61480-6a72-4a2a-a72e-cd39f75a7ef2"
     *    obj: {
     *      "name": "Gold"
     *    }
     *  }
     *}}</pre>
     *
     *  @param paths The paths to be expanded, such as 'customerGroup'. */
    QueryRequest<T> expand(String... paths);
}
