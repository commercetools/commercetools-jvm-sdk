package de.commercetools.sphere.client.model;

/** Result of a call to the bootstrapping webservice that creates new sample projects. */
public class BootstrapResult {
    private String projectKey;
    private String clientId;
    private String clientSecret;

    /** Project key (normalized name) of the created project. */
    public String getProjectKey() {
        return projectKey;
    }

    /** Client id for accessing the created project. */
    public String getClientId() {
        return clientId;
    }

    /** Client secret for accessing the created project. */
    public String getClientSecret() {
        return clientSecret;
    }
}
