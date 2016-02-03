package io.sphere.sdk.customergroups.expansion;

import io.sphere.sdk.customergroups.CustomerGroup;
import io.sphere.sdk.expansion.ExpansionModel;

/**
  DSL class to create expansion path expressions.

 @param <T> the type for which the expansion path is
 */
public final class CustomerGroupExpansionModel<T> extends ExpansionModel<T> {
    public CustomerGroupExpansionModel(final String parentPath, final String path) {
        super(parentPath, path);
    }

    CustomerGroupExpansionModel() {
        super();
    }

    public static CustomerGroupExpansionModel<CustomerGroup> of() {
        return new CustomerGroupExpansionModel<>();
    }
}
