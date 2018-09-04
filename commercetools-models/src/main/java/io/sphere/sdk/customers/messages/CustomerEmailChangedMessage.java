package io.sphere.sdk.customers.messages;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.sphere.sdk.customers.Customer;
import io.sphere.sdk.messages.GenericMessageImpl;
import io.sphere.sdk.messages.MessageDerivateHint;
import io.sphere.sdk.messages.UserProvidedIdentifiers;

import java.time.ZonedDateTime;

/**
 * This message is the result of the {@link io.sphere.sdk.customers.commands.updateactions.ChangeEmail} update action.
 */
@JsonDeserialize(as = CustomerEmailChangedMessage.class)//important to override annotation in Message class
public final class CustomerEmailChangedMessage extends GenericMessageImpl<Customer> {
    public static final String MESSAGE_TYPE = "CustomerEmailChanged";
    public static final MessageDerivateHint<CustomerEmailChangedMessage> MESSAGE_HINT =
            MessageDerivateHint.ofSingleMessageType(MESSAGE_TYPE, CustomerEmailChangedMessage.class, Customer.referenceTypeId());

    private final String email;

    @JsonCreator
    private CustomerEmailChangedMessage(final String id, final Long version, final ZonedDateTime createdAt, final ZonedDateTime lastModifiedAt, final JsonNode resource, final Long sequenceNumber, final Long resourceVersion, final String type, final UserProvidedIdentifiers resourceUserProvidedIdentifiers, final String email) {
        super(id, version, createdAt, lastModifiedAt, resource, sequenceNumber, resourceVersion, type,resourceUserProvidedIdentifiers, Customer.class);
        this.email = email;
    }

    /**
     * @return The updated email of the customer.
     */
    public String getEmail() {
        return email;
    }
}
