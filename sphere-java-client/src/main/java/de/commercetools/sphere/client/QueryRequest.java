package de.commercetools.sphere.client;

import com.google.common.util.concurrent.ListenableFuture;
import de.commercetools.sphere.client.model.QueryResult;

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

    /** Requests {@link de.commercetools.sphere.client.model.Reference}s to be expanded in the returned objects.
     *  Expanded references contain the full target objects they link to.
     *
     *  For example, this is how expanding a path 'vendor' looks on the underlying JSON transport level:
     *  {{{
     *  {
     *    "name": "Product A"
     *    "vendor": {
     *      "typeId": "vendor",
     *      "id": "7ba61480-6a72-4a2a-a72e-cd39f75a7ef2"
     *    }
     *  }
     *  }}}
     *
     *  we obtain:
     *
     *  {{{
     *  {
     *    "name": "Product A"
     *    "owner": {
     *      typeId: "vendor",
     *      id: "7ba61480-6a72-4a2a-a72e-cd39f75a7ef2"
     *      obj: {
     *        "name": "Vendor A",
     *        "imageURLs": []"
     *      }
     *    }
     *  }
     *  }}}
     *
     *  @param paths The paths to be expanded, such as 'vendor', 'categories[*]' or 'variants[*].vendor'. */
    QueryRequest<T> expand(String... paths);

    /** The URL the request will be sent to, useful for debugging purposes. */
    String getUrl();
}
