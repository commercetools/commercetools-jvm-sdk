package io.sphere.internal;

import io.sphere.client.ProjectEndpoints;
import io.sphere.client.QueryRequest;
import io.sphere.client.model.QueryResult;
import io.sphere.client.model.products.BackendCategory;
import io.sphere.client.shop.ApiMode;
import io.sphere.internal.request.RequestFactory;
import com.google.common.base.Optional;
import net.jcip.annotations.Immutable;
import org.codehaus.jackson.type.TypeReference;

@Immutable
public final class CategoriesImpl extends ProjectScopedAPI<BackendCategory> implements Categories {
    public CategoriesImpl(RequestFactory requestFactory, ProjectEndpoints endpoints) {
        super(requestFactory, endpoints, new TypeReference<BackendCategory>() {});
    }

    /** Queries all categories. */
    public QueryRequest<BackendCategory> all() {
        return requestFactory.createQueryRequest(
                endpoints.categories.allSorted(),
                Optional.<ApiMode>absent(),
                new TypeReference<QueryResult<BackendCategory>>() {});
    }
}
