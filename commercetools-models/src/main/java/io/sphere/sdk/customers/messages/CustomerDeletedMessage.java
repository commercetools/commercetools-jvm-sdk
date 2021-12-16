package io.sphere.sdk.customers.messages;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.sphere.sdk.customers.Customer;
import io.sphere.sdk.messages.GenericMessageImpl;
import io.sphere.sdk.messages.MessageDerivateHint;
import io.sphere.sdk.messages.UserProvidedIdentifiers;

import java.time.ZonedDateTime;

@JsonDeserialize(as = CustomerDeletedMessage.class)//important to override annotation in Message class
public final class CustomerDeletedMessage extends GenericMessageImpl<Customer> {
    public static final String MESSAGE_TYPE = "CustomerDeleted";
    public static final MessageDerivateHint<CustomerDeletedMessage> MESSAGE_HINT =
            MessageDerivateHint.ofSingleMessageType(MESSAGE_TYPE, CustomerDeletedMessage.class, Customer.referenceTypeId());

    private final Customer customer;

    @JsonCreator
    private CustomerDeletedMessage(final String id, final Long version, final ZonedDateTime createdAt, final ZonedDateTime lastModifiedAt, final JsonNode resource, final Long sequenceNumber, final Long resourceVersion, final String type, final UserProvidedIdentifiers resourceUserProvidedIdentifiers, final Customer customer) {
        super(id, version, createdAt, lastModifiedAt, resource, sequenceNumber, resourceVersion, type,resourceUserProvidedIdentifiers, Customer.class);
        this.customer = customer;
    }
    
    /**
     * Gets the Customer at creation time
     * 
     * @return the created Customer at creation time
     */
    public Customer getCustomer() {
        return customer;
    }

}
