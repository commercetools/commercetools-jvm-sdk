package io.sphere.sdk.shippingmethods.queries;

import io.sphere.sdk.models.Identifiable;
import io.sphere.sdk.expansion.ExpansionPath;
import io.sphere.sdk.queries.MetaModelGetDsl;
import io.sphere.sdk.shippingmethods.ShippingMethod;
import io.sphere.sdk.shippingmethods.expansion.ShippingMethodExpansionModel;

import java.util.List;
import java.util.function.Function;

/**
 * Fetches a shipping method by ID.
 *
 * {@include.example io.sphere.sdk.shippingmethods.queries.ShippingMethodByIdGetIntegrationTest#execution()}
 */
public interface ShippingMethodByIdGet extends MetaModelGetDsl<ShippingMethod, ShippingMethod, ShippingMethodByIdGet, ShippingMethodExpansionModel<ShippingMethod>> {
    static ShippingMethodByIdGet of(final Identifiable<ShippingMethod> shippingMethod) {
        return of(shippingMethod.getId());
    }

    static ShippingMethodByIdGet of(final String id) {
        return new ShippingMethodByIdGetImpl(id);
    }

    @Override
    List<ExpansionPath<ShippingMethod>> expansionPaths();

    @Override
    ShippingMethodByIdGet plusExpansionPaths(final ExpansionPath<ShippingMethod> expansionPath);

    @Override
    ShippingMethodByIdGet withExpansionPaths(final ExpansionPath<ShippingMethod> expansionPath);

    @Override
    ShippingMethodByIdGet withExpansionPaths(final List<ExpansionPath<ShippingMethod>> expansionPaths);
}

