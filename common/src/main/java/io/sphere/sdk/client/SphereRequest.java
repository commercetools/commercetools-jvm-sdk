package io.sphere.sdk.client;

import io.sphere.sdk.http.HttpResponse;

import java.util.function.Function;

/**
 An argument for a sphere client to execute an HTTP API call on the level of one SPHERE.IO project.

 <p>Typical workflow: A client executes {@link #httpRequestIntent()} and creates a future of an http response. When the future redeems the client uses the http response and passes it as
 argument to {@link #canDeserialize(HttpResponse)}. If the call results in true, the client applies {@link #resultMapper()} to transform the http response into T.</p>

 @param <T> the type which is returned in a successful http request.
 */
public interface SphereRequest<T> {
    /**
     Takes an http response and maps it into a Java object of type T.
     Before calling this method, check with {@link #canDeserialize(HttpResponse)} if the response can be consumed.

     @return function to map the result of the http request
     */
    Function<HttpResponse, T> resultMapper();

    /**
     Provides an http request intent, this does not include the execution of it.
     @return http request intent
     */
    HttpRequestIntent httpRequestIntent();

    /**
     Checks if the response can be handled by {@link #resultMapper()}.

     Use case 1: A http response returns 404 and the this {@link SphereRequest}
     can handle this error by returning an empty optional, an empty list or throwing a domain specific exception.

     @param response the http response which shall be transformed
     @return true if the http response can be consumed, false otherwise
     */
    default boolean canDeserialize(final HttpResponse response) {
        return response.hasSuccessResponseCode() && response.getResponseBody().isPresent();
    }
}
