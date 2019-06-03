package io.sphere.sdk.customers;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.sphere.sdk.models.Timestamped;

import java.time.ZonedDateTime;

/**
 * A token belonging to a customer to verify an email address or resetting the password.
 *
 * @see Customer
 * @see io.sphere.sdk.customers.queries.CustomerByPasswordTokenGet
 * @see io.sphere.sdk.customers.queries.CustomerByEmailTokenGet
 * @see io.sphere.sdk.customers.commands.CustomerCreateEmailTokenCommand
 * @see io.sphere.sdk.customers.commands.CustomerCreatePasswordTokenCommand
 * @see io.sphere.sdk.customers.commands.CustomerPasswordResetCommand
 * @see io.sphere.sdk.customers.commands.CustomerVerifyEmailCommand
 */
@JsonDeserialize(as = CustomerTokenImpl.class)
public interface CustomerToken extends Timestamped {
    /**
     * The ID of the token.
     * @return the id of the token
     */
    String getId();

    /**
     * The ID of the customer belonging to the token
     * @return customer id
     */
    String getCustomerId();

    /**
     * the token value
     * @return secret token
     */
    String getValue();

    ZonedDateTime getExpiresAt();
    
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
    static TypeReference<CustomerToken> typeReference() {
        return new TypeReference<CustomerToken>() {
            @Override
            public String toString() {
                return "TypeReference<CustomerToken>";
            }
        };
    }
}
