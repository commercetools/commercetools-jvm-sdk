package io.sphere.sdk.http;

public interface Requestable {
    /**
     Provides an http request, this does not include the execution of it.
     @return http request
     */
    HttpRequest httpRequestIntent();
}
