package io.sphere.sdk.customers;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.sphere.sdk.carts.Cart;

import javax.annotation.Nullable;

/**
 * A return type to contain a customer and the corresponding cart if there is any.
 *
 * @see io.sphere.sdk.customers.commands.CustomerCreateCommand
 * @see io.sphere.sdk.customers.commands.CustomerSignInCommand
 */
@JsonDeserialize(as = CustomerSignInResultImpl.class)
public interface CustomerSignInResult {
    Customer getCustomer();

    /**
     * A cart belonging to the customer or null.
     * @return cart or null
     */
    @Nullable
    Cart getCart();

    /**
     * Creates a container which contains the full Java type information to deserialize this class from JSON.
     *
     * @see io.sphere.sdk.json.SphereJsonUtils#readObject(byte[], TypeReference)
     * @see io.sphere.sdk.json.SphereJsonUtils#readObject(String, TypeReference)
     * @see io.sphere.sdk.json.SphereJsonUtils#readObject(com.fasterxml.jackson.databind.JsonNode, TypeReference)
     * @see io.sphere.sdk.json.SphereJsonUtils#readObjectFromResource(String, TypeReference)
     *
     * @return type reference
     */
    static TypeReference<CustomerSignInResult> typeReference() {
        return new TypeReference<CustomerSignInResult>() {
            @Override
            public String toString() {
                return "TypeReference<CustomerSignInResult>";
            }
        };
    }
}
