package de.commercetools.sphere.client.util;

import de.commercetools.sphere.client.BackendException;
import com.google.common.util.concurrent.ListenableFuture;

/** Represents a request to the Sphere backend.
 *  Use {@link #fetch} or {@link #fetchAsync} to execute the request. */
public interface RequestBuilder<T> {
    /** Executes the request to the Sphere backend and returns a result. */
    T fetch() throws BackendException;

    /** Executes the request in a non-blocking way and returns a future that provides a notification
     *  when the results from the Sphere backend arrive. */
    ListenableFuture<T> fetchAsync() throws BackendException;

    /** Requests references to be expanded in the returned JSON documents.
     *  Expanded references contain the full target objects they link to.
     *
     *  For example, by expanding a path 'owner' in the following document
     *  {{{
     *  {
     *    "name": "Project A"
     *    "owner": {
     *      "typeId": "user",
     *      "id": "fe12"
     *    }
     *  }
     *  }}}
     *
     *  we obtain:
     *
     *  {{{
     *  {
     *    "name": "Project A"
     *    "owner": {
     *      typeId: "user",
     *      id: "fe12"
     *      obj: {
     *        "firstName": "Jack",
     *        "lastName": "Bauer"
     *      }
     *    }
     *  }
     *  }}}
     *
     *  @param paths The paths to be expanded, such as 'vendor', 'categories[*]' or 'variants[*].vendor'. */
    RequestBuilder<T> expand(String... paths);
}
