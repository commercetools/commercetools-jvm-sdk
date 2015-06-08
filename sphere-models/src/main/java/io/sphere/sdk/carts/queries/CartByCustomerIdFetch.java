package io.sphere.sdk.carts.queries;

import io.sphere.sdk.carts.Cart;
import io.sphere.sdk.carts.expansion.CartExpansionModel;
import io.sphere.sdk.customers.Customer;
import io.sphere.sdk.models.Identifiable;
import io.sphere.sdk.queries.ExpansionPath;
import io.sphere.sdk.queries.MetaModelFetchDsl;

import java.util.List;
import java.util.function.Function;

public interface CartByCustomerIdFetch extends MetaModelFetchDsl<Cart, CartByCustomerIdFetch, CartExpansionModel<Cart>> {
    static CartByCustomerIdFetch of(final Identifiable<Customer> customer) {
        return of(customer.getId());
    }

    static CartByCustomerIdFetch of(final String customerId) {
        return new CartByCustomerIdFetchImpl(customerId);
    }

    @Override
    CartByCustomerIdFetch plusExpansionPath(final Function<CartExpansionModel<Cart>, ExpansionPath<Cart>> m);

    @Override
    CartByCustomerIdFetch withExpansionPath(final Function<CartExpansionModel<Cart>, ExpansionPath<Cart>> m);

    @Override
    List<ExpansionPath<Cart>> expansionPaths();

    @Override
    CartByCustomerIdFetch plusExpansionPath(final ExpansionPath<Cart> expansionPath);

    @Override
    CartByCustomerIdFetch withExpansionPath(final ExpansionPath<Cart> expansionPath);

    @Override
    CartByCustomerIdFetch withExpansionPath(final List<ExpansionPath<Cart>> expansionPaths);
}
