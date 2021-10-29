package io.sphere.sdk.customers.messages;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.sphere.sdk.customers.Customer;
import io.sphere.sdk.customers.CustomerToken;
import io.sphere.sdk.messages.GenericMessageImpl;
import io.sphere.sdk.messages.MessageDerivateHint;
import io.sphere.sdk.messages.UserProvidedIdentifiers;

import java.time.ZonedDateTime;

/**
 * This message is the result of the {@link io.sphere.sdk.customers.commands.CustomerCreatePasswordTokenCommand}.
 */
@JsonDeserialize(as = CustomerPasswordTokenCreatedMessage.class)//important to override annotation in Message class
public final class CustomerPasswordTokenCreatedMessage extends GenericMessageImpl<Customer> {
    public static final String MESSAGE_TYPE = "CustomerPasswordTokenCreated";
    public static final MessageDerivateHint<CustomerPasswordTokenCreatedMessage> MESSAGE_HINT = MessageDerivateHint.ofSingleMessageType(MESSAGE_TYPE, CustomerPasswordTokenCreatedMessage.class, Customer.referenceTypeId());
    private final CustomerToken customerToken;


    @JsonCreator
    public CustomerPasswordTokenCreatedMessage(final String id, final Long version, final ZonedDateTime createdAt, final ZonedDateTime lastModifiedAt, final JsonNode resource, final Long sequenceNumber, final Long resourceVersion, final String type, final UserProvidedIdentifiers resourceUserProvidedIdentifiers, final Class<Customer> clazz, final CustomerToken customerToken) {
        super(id, version, createdAt, lastModifiedAt, resource, sequenceNumber, resourceVersion, type, resourceUserProvidedIdentifiers, clazz);
        this.customerToken = customerToken;
    }

    public CustomerToken getCustomerToken() {
        return this.customerToken;
    }
}
