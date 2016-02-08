package io.sphere.sdk.types.queries;

import io.sphere.sdk.queries.QueryModel;
import io.sphere.sdk.queries.ResourceQueryModelImpl;

/**
 * Internal base class
 * @param <T> resource type
 */
public abstract class CustomResourceQueryModelImpl<T> extends ResourceQueryModelImpl<T> implements WithCustomQueryModel<T> {
    public CustomResourceQueryModelImpl(final QueryModel<T> parent, final String pathSegment) {
        super(parent, pathSegment);
    }

    @Override
    public CustomQueryModel<T> custom() {
        return CustomQueryModel.of(this, "custom");
    }
}
