package io.sphere.sdk.client;

import java.io.Closeable;

/** Provides an OAuth token for accessing protected Sphere HTTP API endpoints. */
interface ClientCredentials extends Closeable {
    /** Returns the OAuth access token. */
    public String getAccessToken();

    public void close();
}
