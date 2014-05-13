package io.sphere.client;

import com.google.common.base.Optional;
import com.google.common.util.concurrent.ListenableFuture;

/** Request that fetches a single object.
 *  <p>Use {@link #fetch} or {@link #fetchAsync} to execute the request. */
public interface FetchRequest<T> {
    /** Executes the request and returns the result, or {@code absent} if the object was not found. */
    Optional<T> fetch();

    /** Executes the request asynchronously and returns a future
     * providing the result, or {@code absent} if not found. */
    ListenableFuture<Optional<T>> fetchAsync();

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
     *  @param paths The paths to be expanded, such as 'customerGroup' */
    FetchRequest<T> expand(String... paths);
}
