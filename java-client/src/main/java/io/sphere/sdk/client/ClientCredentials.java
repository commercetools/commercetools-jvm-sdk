package io.sphere.sdk.client;

/** Provides an OAuth token for accessing protected Sphere HTTP API endpoints. */
interface ClientCredentials {
    /** Returns the OAuth access token. */
    public String getAccessToken();
}
