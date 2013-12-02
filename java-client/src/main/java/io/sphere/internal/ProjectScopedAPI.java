package io.sphere.internal;

import io.sphere.client.ProjectEndpoints;
import io.sphere.internal.request.RequestFactory;

/** Base class for Sphere HTTP APIs scoped to a project. */
abstract class ProjectScopedAPI {
    protected ProjectEndpoints endpoints;
    protected RequestFactory requestFactory;

    protected ProjectScopedAPI(RequestFactory requestFactory, ProjectEndpoints endpoints) {
        this.requestFactory = requestFactory;
        this.endpoints = endpoints;
    }
}
