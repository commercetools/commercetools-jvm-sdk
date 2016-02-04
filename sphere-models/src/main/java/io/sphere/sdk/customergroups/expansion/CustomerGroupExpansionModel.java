package io.sphere.sdk.customergroups.expansion;

import io.sphere.sdk.customergroups.CustomerGroup;
import io.sphere.sdk.expansion.ExpandedModel;

import java.util.List;

/**
  DSL class to create expansion path expressions.

 @param <T> the type for which the expansion path is
 */
public final class CustomerGroupExpansionModel<T> extends ExpandedModel<T> {
    public CustomerGroupExpansionModel(final List<String> parentPath, final String path) {
        super(parentPath, path);
    }

    CustomerGroupExpansionModel() {
        super();
    }

    public static CustomerGroupExpansionModel<CustomerGroup> of() {
        return new CustomerGroupExpansionModel<>();
    }

    public static <T> CustomerGroupExpansionModel<T> of(final List<String> parentPath, final String path) {
        return new CustomerGroupExpansionModel<>(parentPath, path);
    }
}
