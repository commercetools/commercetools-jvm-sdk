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
 * This message is the result of the {@link io.sphere.sdk.customers.commands.CustomerVerifyEmailCommand} command.
 */
@JsonDeserialize(as = CustomerEmailVerifiedMessage.class)//important to override annotation in Message class
public final class CustomerEmailVerifiedMessage extends GenericMessageImpl<Customer> {
    public static final String MESSAGE_TYPE = "CustomerEmailVerified";
    public static final MessageDerivateHint<CustomerEmailVerifiedMessage> MESSAGE_HINT =
            MessageDerivateHint.ofSingleMessageType(MESSAGE_TYPE, CustomerEmailVerifiedMessage.class, Customer.referenceTypeId());

    @JsonCreator
    private CustomerEmailVerifiedMessage(final String id, final Long version, final ZonedDateTime createdAt, final ZonedDateTime lastModifiedAt, final JsonNode resource, final Long sequenceNumber, final Long resourceVersion, final String type, final UserProvidedIdentifiers resourceUserProvidedIdentifiers) {
        super(id, version, createdAt, lastModifiedAt, resource, sequenceNumber, resourceVersion, type,resourceUserProvidedIdentifiers, Customer.class);
    }

}
