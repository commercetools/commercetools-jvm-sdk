package io.sphere.internal;

import io.sphere.client.FetchRequest;
import io.sphere.client.ProjectEndpoints;
import io.sphere.client.QueryRequest;
import io.sphere.client.model.QueryResult;
import io.sphere.client.shop.ApiMode;
import io.sphere.client.shop.TaxCategoryService;
import io.sphere.client.shop.model.TaxCategory;
import io.sphere.internal.request.RequestFactory;
import com.google.common.base.Optional;
import org.codehaus.jackson.type.TypeReference;

public class TaxCategoryServiceImpl  extends ProjectScopedAPI<TaxCategory> implements TaxCategoryService {
    public TaxCategoryServiceImpl(RequestFactory requestFactory, ProjectEndpoints endpoints) {
        super(requestFactory, endpoints, new TypeReference<TaxCategory>() {}, new TypeReference<QueryResult<TaxCategory>>() { } );
    }

    @Override public FetchRequest<TaxCategory> byId(String id) {
        return requestFactory.createFetchRequest(
                endpoints.taxCategories.byId(id),
                Optional.<ApiMode>absent(),
                new TypeReference<TaxCategory>() {});
    }

    @Deprecated
    @Override public QueryRequest<TaxCategory> all() {
        return query();
    }

    @Override
    public QueryRequest<TaxCategory> query() {
        return queryImpl(endpoints.taxCategories.root());
    }
}
