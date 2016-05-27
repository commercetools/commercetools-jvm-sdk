package io.sphere.sdk.client;

import io.sphere.sdk.http.HttpResponse;

import javax.annotation.Nullable;

/**
 An argument for a sphere client to execute an HTTP API call on the level of one project.

 <p>Typical workflow: A client executes {@link #httpRequestIntent()} and creates a future of an http response. When the future redeems the client uses the http response and passes it as
 argument to {@link #canDeserialize(HttpResponse)}. If the call results in true, the client applies {@link #deserialize(HttpResponse)} to transform the http response into T.</p>

 It is intented that this class is immutable, so that every method can be used in a pure functional way.

 @param <T> the type which is returned in a successful http request.
 */
public interface SphereRequest<T> {
    /**
     Takes an http response and maps it into a Java object of type T.
     Before calling this method, check with {@link #canDeserialize(HttpResponse)} if the response can be consumed.

     @return the deserialized object
     @param httpResponse the http response of the platform
     */
    @Nullable
    T deserialize(final HttpResponse httpResponse);

    /**
     Provides an http request intent, this does not include the execution of it.
     @return http request intent
     */
    HttpRequestIntent httpRequestIntent();

    /**
     Checks if the response can be handled by {@link #deserialize(HttpResponse)}.

     Use case 1: A http response returns 404 and the this {@link SphereRequest}
     can handle this error by returning an empty optional, an empty list or throwing a domain specific exception.

     @param httpResponse the http response which shall be transformed
     @return true if the http response can be consumed, false otherwise
     */
    default boolean canDeserialize(final HttpResponse httpResponse) {
        return httpResponse.hasSuccessResponseCode() && httpResponse.getResponseBody() != null;
    }
}
