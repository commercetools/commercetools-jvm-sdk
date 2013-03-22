package io.sphere.client;

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
    FetchRequest<T> expand(String... paths);
}
