package io.sphere.sdk.customergroups;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.sphere.sdk.carts.Cart;
import io.sphere.sdk.customers.Customer;
import io.sphere.sdk.models.Resource;
import io.sphere.sdk.models.Reference;

/**
 * <p>A Customer can be a member of a customer group (e.g. reseller, gold member). Special prices can be assigned to specific products based on a customer group.</p>
 *
 * @see io.sphere.sdk.customergroups.commands.CustomerGroupCreateCommand
 * @see io.sphere.sdk.customergroups.commands.CustomerGroupUpdateCommand
 * @see io.sphere.sdk.customergroups.commands.CustomerGroupDeleteCommand
 * @see io.sphere.sdk.customergroups.queries.CustomerGroupQuery
 * @see io.sphere.sdk.customergroups.queries.CustomerGroupByIdGet
 * @see Customer#getCustomerGroup()
 * @see io.sphere.sdk.customers.commands.updateactions.SetCustomerGroup
 * @see Cart#getCustomerGroup()
 * @see io.sphere.sdk.orders.Order#getCustomerGroup()
 * @see io.sphere.sdk.products.Price#getCustomerGroup()
 */
@JsonDeserialize(as = CustomerGroupImpl.class)
public interface CustomerGroup extends Resource<CustomerGroup> {

    String getName();

    @Override
    default Reference<CustomerGroup> toReference() {
        return Reference.of(referenceTypeId(), getId(), this);
    }

    static String referenceTypeId() {
        return "customer-group";
    }

    /**
     *
     * @deprecated use {@link #referenceTypeId()} instead
     * @return referenceTypeId
     */
    @Deprecated
    static String typeId(){
        return "customer-group";
    }

    static TypeReference<CustomerGroup> typeReference(){
        return new TypeReference<CustomerGroup>() {
            @Override
            public String toString() {
                return "TypeReference<CustomerGroup>";
            }
        };
    }

    static Reference<CustomerGroup> referenceOfId(final String id) {
        return Reference.of(referenceTypeId(), id);
    }
}
