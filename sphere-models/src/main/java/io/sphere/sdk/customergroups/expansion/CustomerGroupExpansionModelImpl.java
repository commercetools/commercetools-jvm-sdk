package io.sphere.sdk.customergroups.expansion;

import io.sphere.sdk.expansion.ExpandedModel;

import java.util.List;

final class CustomerGroupExpansionModelImpl<T> extends ExpandedModel<T> implements CustomerGroupExpansionModel<T> {
    CustomerGroupExpansionModelImpl(final List<String> parentPath, final String path) {
        super(parentPath, path);
    }

    CustomerGroupExpansionModelImpl() {
        super();
    }
}
