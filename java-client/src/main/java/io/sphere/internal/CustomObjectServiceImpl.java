package io.sphere.internal;

import io.sphere.client.ProjectEndpoints;
import io.sphere.client.customobjects.CustomObjectService;
import io.sphere.internal.request.RequestFactory;

public class CustomObjectServiceImpl extends ProjectScopedAPI implements CustomObjectService {
    public CustomObjectServiceImpl(RequestFactory requestFactory, ProjectEndpoints endpoints) {
        super(endpoints);
    }
}
