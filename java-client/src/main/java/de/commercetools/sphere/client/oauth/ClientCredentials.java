package io.sphere.client.oauth;

/** Provides an OAuth access token for accessing protected Sphere HTTP API endpoints. */
public interface ClientCredentials {
    public String accessToken();
}
