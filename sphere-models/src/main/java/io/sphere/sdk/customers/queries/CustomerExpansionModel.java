package io.sphere.sdk.customers.queries;

import io.sphere.sdk.queries.ExpansionModel;
import io.sphere.sdk.queries.ExpansionPath;

public class CustomerExpansionModel<T> extends ExpansionModel<T> {
    CustomerExpansionModel() {
        super();
    }

    ExpansionPath<T> customerGroup() {
        return ExpansionPath.of("customerGroup");
    }
}
