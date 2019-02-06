package io.sphere.sdk.orderedits.expansion;

import io.sphere.sdk.expansion.ExpansionPathContainer;
import io.sphere.sdk.orderedits.OrderEdit;

import java.util.List;

public interface OrderEditExpansionModel<T> extends ExpansionPathContainer<T> {

    static OrderEditExpansionModel<OrderEdit> of() {
        return new OrderEditExpansionModelImpl<>();
    }

    static <T> OrderEditExpansionModel<T> of(final List<String> parentPath, final String path) {
        return new OrderEditExpansionModelImpl<>(parentPath, path);
    }

}
