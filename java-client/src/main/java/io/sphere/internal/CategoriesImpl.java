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
public final class CategoriesImpl extends ProjectScopedAPI implements Categories {
    private final RequestFactory requestFactory;

    public CategoriesImpl(RequestFactory requestFactory, ProjectEndpoints endpoints) {
        super(endpoints);
        this.requestFactory = requestFactory;
    }

    /** Queries all categories. */
    public QueryRequest<BackendCategory> all() {
        return requestFactory.createQueryRequest(
                endpoints.categories.allCategoriesSorted(),
                Optional.<ApiMode>absent(),
                new TypeReference<QueryResult<BackendCategory>>() {});
    }
}
