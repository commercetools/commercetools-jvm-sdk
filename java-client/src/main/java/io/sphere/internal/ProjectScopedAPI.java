package io.sphere.internal;

import com.google.common.base.Optional;
import io.sphere.client.CommandRequest;
import io.sphere.client.ProjectEndpoints;
import io.sphere.client.QueryRequest;
import io.sphere.client.model.QueryResult;
import io.sphere.client.shop.ApiMode;
import io.sphere.internal.command.Command;
import io.sphere.internal.request.RequestFactory;
import org.codehaus.jackson.type.TypeReference;

/** Base class for Sphere HTTP APIs scoped to a project. */
abstract class ProjectScopedAPI<T> {
    protected ProjectEndpoints endpoints;
    protected RequestFactory requestFactory;
    protected TypeReference<T> typeReference;
    protected TypeReference<QueryResult<T>> queryResultTypeReference;

    protected ProjectScopedAPI(RequestFactory requestFactory, ProjectEndpoints endpoints, TypeReference<T> typeReference, TypeReference<QueryResult<T>> queryResultTypeReference) {
        this.requestFactory = requestFactory;
        this.endpoints = endpoints;
        this.typeReference = typeReference;
        this.queryResultTypeReference = queryResultTypeReference;
    }

    protected CommandRequest<T> createCommandRequest(String url, Command command) {
        return requestFactory.createCommandRequest(url, command, typeReference);
    }

    protected QueryRequest<T> queryImpl(String url) {
        return requestFactory.createQueryRequest(url, Optional.<ApiMode>absent(), queryResultTypeReference);
    }
}
