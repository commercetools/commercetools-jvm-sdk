package de.commercetools.internal;

import de.commercetools.sphere.client.model.products.BackendCategory;
import de.commercetools.internal.request.RequestFactory;
import de.commercetools.sphere.client.ProjectEndpoints;
import de.commercetools.sphere.client.QueryRequest;
import de.commercetools.sphere.client.model.QueryResult;
import net.jcip.annotations.Immutable;
import org.codehaus.jackson.type.TypeReference;

@Immutable
public final class CategoriesImpl extends ProjectScopedAPI implements Categories {
    private final RequestFactory requestFactory;

    public CategoriesImpl(RequestFactory requestFactory, ProjectEndpoints endpoints) {
        super(endpoints);
        this.requestFactory = requestFactory;
    }

    /** Queries all categories. */
    public QueryRequest<BackendCategory> all() {
        return requestFactory.createQueryRequest(endpoints.categories.root(), new TypeReference<QueryResult<BackendCategory>>() {});
    }
}
