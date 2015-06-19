package io.sphere.sdk.customergroups.expansion;

import io.sphere.sdk.customergroups.CustomerGroup;
import io.sphere.sdk.expansion.ExpansionModel;

import java.util.Optional;

/**
  DSL class to create expansion path expressions.

 @param <T> the type for which the expansion path is
 */
public class CustomerGroupExpansionModel<T> extends ExpansionModel<T> {
    public CustomerGroupExpansionModel(final Optional<String> parentPath, final String path) {
        super(parentPath, Optional.of(path));
    }

    CustomerGroupExpansionModel() {
        super();
    }

    public static CustomerGroupExpansionModel<CustomerGroup> of() {
        return new CustomerGroupExpansionModel<>();
    }
}
