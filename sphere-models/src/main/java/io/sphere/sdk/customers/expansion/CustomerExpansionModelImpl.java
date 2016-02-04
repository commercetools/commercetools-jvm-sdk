package io.sphere.sdk.customers.expansion;

import io.sphere.sdk.customergroups.expansion.CustomerGroupExpansionModel;
import io.sphere.sdk.expansion.ExpansionModelImpl;

import javax.annotation.Nullable;
import java.util.List;

final class CustomerExpansionModelImpl<T> extends ExpansionModelImpl<T> implements CustomerExpansionModel<T> {
    CustomerExpansionModelImpl() {
        super();
    }

    CustomerExpansionModelImpl(@Nullable final List<String> parentPath, @Nullable final String path) {
        super(parentPath, path);
    }

    @Override
    public CustomerGroupExpansionModel<T> customerGroup() {
        return CustomerGroupExpansionModel.of(buildPathExpression(), "customerGroup");
    }
}
