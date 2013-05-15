package io.sphere.internal;

import io.sphere.client.ProjectEndpoints;

/** Base class for Sphere HTTP APIs scoped to a project. */
abstract class ProjectScopedAPI {
    protected ProjectEndpoints endpoints;

    protected ProjectScopedAPI(ProjectEndpoints endpoints) {
        this.endpoints = endpoints;
    }
}
