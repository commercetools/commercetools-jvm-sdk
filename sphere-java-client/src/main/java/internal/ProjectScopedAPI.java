package de.commercetools.internal;

import de.commercetools.sphere.client.ProjectEndpoints;
import de.commercetools.sphere.client.oauth.ClientCredentials;

/** Base class for Sphere HTTP APIs scoped to a project. */
abstract class ProjectScopedAPI {
    protected ProjectEndpoints endpoints;

    protected ProjectScopedAPI(ProjectEndpoints endpoints) {
        this.endpoints = endpoints;
    }
}
