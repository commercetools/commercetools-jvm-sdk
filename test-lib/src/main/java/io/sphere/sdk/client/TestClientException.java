package io.sphere.sdk.client;

public class TestClientException extends RuntimeException {
    private static final long serialVersionUID = 4954918890077093843L;

    public TestClientException(final Exception e) {
        super(e);
    }
}
