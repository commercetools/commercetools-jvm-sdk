package io.sphere.internal;

import com.google.common.base.Optional;
import io.sphere.client.shop.ApiMode;
import io.sphere.internal.request.RequestFactory;
import io.sphere.client.FetchRequest;
import io.sphere.client.ProjectEndpoints;
import io.sphere.client.model.QueryResult;
import io.sphere.client.model.Reference;
import io.sphere.client.shop.InventoryService;
import io.sphere.client.shop.model.Catalog;
import io.sphere.client.shop.model.InventoryEntry;
import org.codehaus.jackson.type.TypeReference;

public class InventoryServiceImpl extends ProjectScopedAPI implements InventoryService {
    private final RequestFactory requestFactory;

    public InventoryServiceImpl(RequestFactory requestFactory, ProjectEndpoints endpoints) {
        super(endpoints);
        this.requestFactory = requestFactory;
    }

    /** {@inheritDoc}  */
    public FetchRequest<InventoryEntry> byId(String id) {
        return requestFactory.createFetchRequest(
                endpoints.inventory.byId(id),
                Optional.<ApiMode>absent(),
                new TypeReference<InventoryEntry>() {});
    }

    /** {@inheritDoc}  */
    public FetchRequest<InventoryEntry> byVariantInCatalog(String productId, String variantId, Reference<Catalog> catalog) {
        return requestFactory.createFetchRequestBasedOnQuery(
                endpoints.inventory.byVariantInCatalog(productId, variantId, catalog),
                Optional.<ApiMode>absent(),
                new TypeReference<QueryResult<InventoryEntry>>() {});
    }

    /** {@inheritDoc}  */
    public FetchRequest<InventoryEntry> byVariantInMasterCatalog(String productId, String variantId) {
        return byVariantInCatalog(productId, variantId, null);
    }
}
