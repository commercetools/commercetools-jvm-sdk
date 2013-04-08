package io.sphere.internal;

import com.google.common.base.Optional;
import io.sphere.client.shop.ApiMode;
import io.sphere.internal.request.RequestFactory;
import io.sphere.client.FetchRequest;
import io.sphere.client.ProjectEndpoints;
import io.sphere.client.model.QueryResult;
import io.sphere.client.shop.InventoryService;
import io.sphere.client.shop.model.InventoryEntry;
import org.codehaus.jackson.type.TypeReference;

public class InventoryServiceImpl extends ProjectScopedAPI implements InventoryService {
    private final RequestFactory requestFactory;

    public InventoryServiceImpl(RequestFactory requestFactory, ProjectEndpoints endpoints) {
        super(endpoints);
        this.requestFactory = requestFactory;
    }

    @Override public FetchRequest<InventoryEntry> byId(String id) {
        return requestFactory.createFetchRequest(
                endpoints.inventory.byId(id),
                Optional.<ApiMode>absent(),
                new TypeReference<InventoryEntry>() {});
    }

    @Override public FetchRequest<InventoryEntry> byProductVariant(String productId, String variantId) {
        return requestFactory.createFetchRequestBasedOnQuery(
                endpoints.inventory.byVariantInCatalog(productId, variantId, null),
                Optional.<ApiMode>absent(),
                new TypeReference<QueryResult<InventoryEntry>>() {});
    }
}
