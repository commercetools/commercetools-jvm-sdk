package io.sphere.sdk.client;

import io.sphere.sdk.models.SphereException;
import io.sphere.sdk.http.HttpResponse;

import java.util.function.Function;
import java.util.function.Predicate;

final class HttpResponseExceptionResponsibility {
    private final Predicate<HttpResponse> predicate;
    private final Function<HttpResponse, SphereException> exceptionCreator;

    public HttpResponseExceptionResponsibility(final Predicate<HttpResponse> predicate, final Function<HttpResponse, SphereException> exceptionCreator) {
        this.exceptionCreator = exceptionCreator;
        this.predicate = predicate;
    }

    public Function<HttpResponse, SphereException> getExceptionCreator() {
        return exceptionCreator;
    }

    public Predicate<HttpResponse> getPredicate() {
        return predicate;
    }
}
