package io.sphere.sdk.client;

public class InvalidClientCredentialsException extends UnauthorizedException {
    private static final long serialVersionUID = 0L;

    public InvalidClientCredentialsException(final SphereAuthConfig config) {
        super("Invalid credentials for " + config.getProjectKey() + " on " + config.getAuthUrl());
    }
}
