package io.sphere.sdk.customers.messages;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.sphere.sdk.customers.Customer;
import io.sphere.sdk.messages.GenericMessageImpl;
import io.sphere.sdk.messages.MessageDerivateHint;
import io.sphere.sdk.messages.UserProvidedIdentifiers;
import io.sphere.sdk.models.Address;

import java.time.ZonedDateTime;

/**
 * This message is the result of the {@link io.sphere.sdk.customers.commands.updateactions.ChangeAddress} update action.
 */
@JsonDeserialize(as = CustomerAddressChangedMessage.class)//important to override annotation in Message class
public final class CustomerAddressChangedMessage extends GenericMessageImpl<Customer> {
    public static final String MESSAGE_TYPE = "CustomerAddressChanged";
    public static final MessageDerivateHint<CustomerAddressChangedMessage> MESSAGE_HINT =
            MessageDerivateHint.ofSingleMessageType(MESSAGE_TYPE, CustomerAddressChangedMessage.class, Customer.referenceTypeId());

    private final Address address;

    @JsonCreator
    private CustomerAddressChangedMessage(final String id, final Long version, final ZonedDateTime createdAt, final ZonedDateTime lastModifiedAt, final JsonNode resource, final Long sequenceNumber, final Long resourceVersion, final String type, final UserProvidedIdentifiers resourceUserProvidedIdentifiers, final Address address) {
        super(id, version, createdAt, lastModifiedAt, resource, sequenceNumber, resourceVersion, type,resourceUserProvidedIdentifiers, Customer.class);
        this.address = address;
    }

    /**
     * @return The updated address of the customer
     */
    public Address getAddress() {
        return address;
    }
}
