package io.sphere.sdk.requests;

import java.util.function.Function;

public interface ClientRequest<T> extends Requestable {
    Function<HttpResponse, T> resultMapper();
}
