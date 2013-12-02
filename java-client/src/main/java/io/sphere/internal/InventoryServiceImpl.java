package io.sphere.internal;

import io.sphere.client.ProjectEndpoints;
import io.sphere.client.shop.InventoryService;
import io.sphere.internal.request.RequestFactory;

public class InventoryServiceImpl extends ProjectScopedAPI implements InventoryService {
    private final RequestFactory requestFactory;

    public InventoryServiceImpl(RequestFactory requestFactory, ProjectEndpoints endpoints) {
        super(endpoints);
        this.requestFactory = requestFactory;
    }
}
