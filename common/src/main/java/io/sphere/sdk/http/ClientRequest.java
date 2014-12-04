package io.sphere.sdk.http;

import java.util.function.Function;

public interface ClientRequest<T> extends Requestable {
    Function<HttpResponse, T> resultMapper();

    default boolean canHandleResponse(final HttpResponse response) {
        return response.hasSuccessResponseCode() && response.getResponseBody().isPresent();
    }
}
