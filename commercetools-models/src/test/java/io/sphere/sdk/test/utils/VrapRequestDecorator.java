package io.sphere.sdk.test.utils;

import io.sphere.sdk.client.HttpRequestIntent;
import io.sphere.sdk.client.SphereRequest;
import io.sphere.sdk.client.SphereRequestDecorator;
import io.sphere.sdk.http.HttpHeaders;

/**
 * This sphere decorator allows us to control the vrap validation by sending
 * vrap specific http headers.
 */
public class VrapRequestDecorator<T> extends SphereRequestDecorator<T> {
    private final HttpHeaders headers;

    public VrapRequestDecorator(final SphereRequest<T> delegate, final String... validationFlags) {
        super(delegate);
        headers = VrapHeaders.disableValidation(validationFlags);
    }

    @Override
    public HttpRequestIntent httpRequestIntent() {
        return super.httpRequestIntent().withHeaders(headers);
    }
}
