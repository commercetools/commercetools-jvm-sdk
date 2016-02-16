package io.sphere.sdk.carts.queries;

import io.sphere.sdk.carts.Cart;
import io.sphere.sdk.carts.expansion.CartExpansionModel;
import io.sphere.sdk.customers.Customer;
import io.sphere.sdk.expansion.ExpansionPath;
import io.sphere.sdk.models.Identifiable;
import io.sphere.sdk.queries.MetaModelGetDsl;

import java.util.List;

/**
 * Gets a cart by the customer id.
 *
 * {@include.example io.sphere.sdk.carts.queries.CartByCustomerIdGetIntegrationTest#execution()}
 *
 * @see Customer#getId()
 */
public interface CartByCustomerIdGet extends MetaModelGetDsl<Cart, Cart, CartByCustomerIdGet, CartExpansionModel<Cart>> {
    static CartByCustomerIdGet of(final Identifiable<Customer> customer) {
        return of(customer.getId());
    }

    static CartByCustomerIdGet of(final String customerId) {
        return new CartByCustomerIdGetImpl(customerId);
    }

    @Override
    List<ExpansionPath<Cart>> expansionPaths();

    @Override
    CartByCustomerIdGet plusExpansionPaths(final ExpansionPath<Cart> expansionPath);

    @Override
    CartByCustomerIdGet withExpansionPaths(final ExpansionPath<Cart> expansionPath);

    @Override
    CartByCustomerIdGet withExpansionPaths(final List<ExpansionPath<Cart>> expansionPaths);
}
