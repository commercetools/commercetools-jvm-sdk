package io.sphere.sdk.customergroups.expansion;

import io.sphere.sdk.expansion.ExpansionModelImpl;

import java.util.List;

final class CustomerGroupExpansionModelImpl<T> extends ExpansionModelImpl<T> implements CustomerGroupExpansionModel<T> {
    CustomerGroupExpansionModelImpl(final List<String> parentPath, final String path) {
        super(parentPath, path);
    }

    CustomerGroupExpansionModelImpl() {
        super();
    }
}
