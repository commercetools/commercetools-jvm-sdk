package de.commercetools.sphere.client.shop;

import de.commercetools.sphere.client.ProjectEndpoints;
import de.commercetools.sphere.client.util.RequestBuilder;
import de.commercetools.sphere.client.util.RequestBuilderImpl;
import de.commercetools.sphere.client.oauth.ClientCredentials;
import com.ning.http.client.AsyncHttpClient;
import org.codehaus.jackson.type.TypeReference;

/** Base class for Sphere HTTP APIs scoped to a project. */
abstract class ProjectScopedAPI {

    protected ProjectEndpoints endpoints;
    protected ClientCredentials credentials;

    protected ProjectScopedAPI(ClientCredentials credentials, ProjectEndpoints endpoints) {
        this.endpoints = endpoints;
        this.credentials = credentials;
    }
}
