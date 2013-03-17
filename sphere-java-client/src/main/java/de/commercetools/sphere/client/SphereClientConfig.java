package de.commercetools.sphere.client;

/** The configuration for a {@link SphereClient}. */
public interface SphereClientConfig {
    /** Sphere HTTP API endpoint. */
    String getCoreHttpServiceUrl();
    /** Sphere OAuth2 endpoint. */
    String getAuthHttpServiceUrl();
}