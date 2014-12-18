package io.sphere.sdk.http;

import java.util.function.Function;

/**
 An argument for a sphere client to execute an HTTP API call on the level of one SPHERE.IO project.

 <p>Typical workflow: A client executes {@link #httpRequest()} and creates a future of an http response. When the future redeems the client uses the http response and passes it as
 argument to {@link #canHandleResponse(HttpResponse)}. If the call results in true, the client applies {@link #resultMapper()} to transform the http response into T.</p>

 @param <T> the type which is returned in a successful http request.

 */
public interface ClientRequest<T> extends Requestable {
    /**
     Takes an http response and maps it into a Java object of type T.
     Before calling this method, check with {@link #canHandleResponse(HttpResponse)} if the response can be consumed.
     @return
     */
    Function<HttpResponse, T> resultMapper();

    @Override
    HttpRequest httpRequest();

    /**
     Checks if the response can be handled by {@link #resultMapper()}.

     Use case 1: A http response returns 404 and the this {@link io.sphere.sdk.http.ClientRequest}
     can handle this error by returning an empty optional, an empty list or throwing a domain specific exception.

     @param response the http response which shall be transformed
     @return true if the http response can be consumed, false otherwise
     */
    default boolean canHandleResponse(final HttpResponse response) {
        return response.hasSuccessResponseCode() && response.getResponseBody().isPresent();
    }
}
