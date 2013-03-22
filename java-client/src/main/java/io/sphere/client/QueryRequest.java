package io.sphere.client;

import com.google.common.util.concurrent.ListenableFuture;
import io.sphere.internal.request.TestableRequest;
import io.sphere.internal.request.TestableRequestHolder;
import io.sphere.client.model.QueryResult;

/** Represents a request that queries for multiple objects.
 *  Use {@link #fetch} or {@link #fetchAsync} to execute the request. */
public interface QueryRequest<T> {
    /** Executes the request to the Sphere backend and returns a result. */
    QueryResult<T> fetch();

    /** Executes the request toe the Sphere backend in a non-blocking way and returns a future of the results. */
    ListenableFuture<QueryResult<T>> fetchAsync();

    /** Sets the page number for paging through results. Page numbers start at zero. */
    QueryRequest<T> page(int page);

    /** Sets the size of a page for paging through results. When page size is not set, the default of 10 is used. */
    QueryRequest<T> pageSize(int pageSize);

    /** Requests {@linkplain io.sphere.client.model.Reference Reference fields} to be expanded in the returned objects.
     *  Expanded references contain full target objects they link to.
     *
     *  <p>For example, this is how expanding a customer group of a customer looks at the underlying JSON transport level:
     *  <pre>{@code
     *  {
     *    "name": "John Doe"
     *    "customerGroup": {
     *      "typeId": "customergroup",
     *      "id": "7ba61480-6a72-4a2a-a72e-cd39f75a7ef2"
     *    }
     *  }
     *  }</pre>
     *
     *  <pre>{@code
     *  {
     *    "name": "John Doe"
     *    "customerGroup": {
     *      typeId: "customergroup",
     *      id: "7ba61480-6a72-4a2a-a72e-cd39f75a7ef2"
     *      obj: {
     *        "name": "Gold",
     *      }
     *    }
     *  }
     *  }</pre>
     *
     *  @param paths The paths to be expanded, such as 'parent'. */
    QueryRequest<T> expand(String... paths);
}
