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
 * This message is the result of the {@link io.sphere.sdk.customers.commands.CustomerChangePasswordCommand}.
 */
@JsonDeserialize(as = CustomerPasswordUpdatedMessage.class)//important to override annotation in Message class
public final class CustomerPasswordUpdatedMessage extends GenericMessageImpl<Customer> {
    public static final String MESSAGE_TYPE = "CustomerPasswordUpdated";
    public static final MessageDerivateHint<CustomerPasswordUpdatedMessage> MESSAGE_HINT =
            MessageDerivateHint.ofSingleMessageType(MESSAGE_TYPE, CustomerPasswordUpdatedMessage.class, Customer.referenceTypeId());

    private final Boolean reset;

    @JsonCreator
    private CustomerPasswordUpdatedMessage(final String id, final Long version, final ZonedDateTime createdAt, final ZonedDateTime lastModifiedAt, final JsonNode resource, final Long sequenceNumber, final Long resourceVersion, final String type, final UserProvidedIdentifiers resourceUserProvidedIdentifiers, final Boolean reset) {
        super(id, version, createdAt, lastModifiedAt, resource, sequenceNumber, resourceVersion, type,resourceUserProvidedIdentifiers, Customer.class);
        this.reset = reset;
    }

    /**
     * @return If password has been updated during customers password reset workflow
     */
    public Boolean getReset() {
        return reset;
    }
}
