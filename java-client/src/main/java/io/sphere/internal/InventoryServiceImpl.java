package io.sphere.internal;

import io.sphere.client.ProjectEndpoints;
import io.sphere.client.shop.InventoryService;
import io.sphere.client.shop.model.InventoryEntry;
import io.sphere.internal.request.RequestFactory;
import org.codehaus.jackson.type.TypeReference;

public class InventoryServiceImpl extends ProjectScopedAPI<InventoryEntry> implements InventoryService {
    public InventoryServiceImpl(RequestFactory requestFactory, ProjectEndpoints endpoints) {
        super(requestFactory, endpoints, new TypeReference<InventoryEntry>() {});
    }
}
