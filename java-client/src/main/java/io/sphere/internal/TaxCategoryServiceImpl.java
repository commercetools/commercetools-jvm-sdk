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

public class TaxCategoryServiceImpl  extends ProjectScopedAPI implements TaxCategoryService {
    private final RequestFactory requestFactory;
    
    public TaxCategoryServiceImpl(RequestFactory requestFactory, ProjectEndpoints endpoints) {
        super(endpoints);
        this.requestFactory = requestFactory;
    }

    @Override public FetchRequest<TaxCategory> byId(String id) {
        return requestFactory.createFetchRequest(
                endpoints.taxCategories.byId(id),
                Optional.<ApiMode>absent(),
                new TypeReference<TaxCategory>() {});
    }

    @Override public QueryRequest<TaxCategory> all() {
        return requestFactory.createQueryRequest(
                endpoints.taxCategories.root(),
                Optional.<ApiMode>absent(),
                new TypeReference<QueryResult<TaxCategory>>() {});
    }

}
