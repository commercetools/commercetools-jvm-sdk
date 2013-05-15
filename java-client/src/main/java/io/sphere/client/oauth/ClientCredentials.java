package io.sphere.client.oauth;

/** Provides an OAuth token for accessing protected Sphere HTTP API endpoints. */
public interface ClientCredentials {
    public String getAccessToken();
}
