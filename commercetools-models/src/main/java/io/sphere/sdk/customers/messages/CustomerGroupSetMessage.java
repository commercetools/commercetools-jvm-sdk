package io.sphere.sdk.customers.messages;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.sphere.sdk.customergroups.CustomerGroup;
import io.sphere.sdk.customers.Customer;
import io.sphere.sdk.messages.GenericMessageImpl;
import io.sphere.sdk.messages.MessageDerivateHint;
import io.sphere.sdk.messages.UserProvidedIdentifiers;
import io.sphere.sdk.models.Reference;

import java.time.ZonedDateTime;

/**
 * This message is the result of the {@link io.sphere.sdk.customers.commands.updateactions.SetCustomerGroup} update action.
 */
@JsonDeserialize(as = CustomerGroupSetMessage.class)//important to override annotation in Message class
public final class CustomerGroupSetMessage extends GenericMessageImpl<Customer> {
    public static final String MESSAGE_TYPE = "CustomerGroupSet";
    public static final MessageDerivateHint<CustomerGroupSetMessage> MESSAGE_HINT =
            MessageDerivateHint.ofSingleMessageType(MESSAGE_TYPE, CustomerGroupSetMessage.class, Customer.referenceTypeId());

    private final Reference<CustomerGroup> customerGroup;

    @JsonCreator
    private CustomerGroupSetMessage(final String id, final Long version, final ZonedDateTime createdAt, final ZonedDateTime lastModifiedAt, final JsonNode resource, final Long sequenceNumber, final Long resourceVersion, final String type, final UserProvidedIdentifiers resourceUserProvidedIdentifiers, final Reference<CustomerGroup> customerGroup) {
        super(id, version, createdAt, lastModifiedAt, resource, sequenceNumber, resourceVersion, type,resourceUserProvidedIdentifiers, Customer.class);
        this.customerGroup = customerGroup;
    }

    public Reference<CustomerGroup> getCustomerGroup() {
        return customerGroup;
    }
}
