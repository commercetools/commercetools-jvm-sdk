package de.commercetools.sphere.client;

import com.google.common.base.Optional;
import com.google.common.util.concurrent.ListenableFuture;

/** Represents a request that fetches a single object.
 *  Use {@link #fetch} or {@link #fetchAsync} to execute the request. */
public interface FetchRequest<T> {
    /** Executes the request to the Sphere backend and returns a result.
     *  Returns 'absent' if the object was not found. */
    Optional<T> fetch();

    /** Executes the request to the Sphere backend in a non-blocking way and returns a future with the object,
     *  or 'absent' if not found. */
    ListenableFuture<Optional<T>> fetchAsync();

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
    FetchRequest<T> expand(String... paths);

    /** The URL the request will be sent to, useful for debugging purposes. */
    String getUrl();
}
