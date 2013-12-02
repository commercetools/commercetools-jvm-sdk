package io.sphere.internal;

import io.sphere.client.CommandRequest;
import io.sphere.client.ProjectEndpoints;
import io.sphere.internal.command.Command;
import io.sphere.internal.request.RequestFactory;
import org.codehaus.jackson.type.TypeReference;

/** Base class for Sphere HTTP APIs scoped to a project. */
abstract class ProjectScopedAPI<T> {
    protected ProjectEndpoints endpoints;
    protected RequestFactory requestFactory;
    protected TypeReference<T> typeReference;

    protected ProjectScopedAPI(RequestFactory requestFactory, ProjectEndpoints endpoints, TypeReference<T> typeReference) {
        this.requestFactory = requestFactory;
        this.endpoints = endpoints;
        this.typeReference = typeReference;
    }

    protected CommandRequest<T> createCommandRequest(String url, Command command) {
        return requestFactory.createCommandRequest(url, command, typeReference);
    }
}
