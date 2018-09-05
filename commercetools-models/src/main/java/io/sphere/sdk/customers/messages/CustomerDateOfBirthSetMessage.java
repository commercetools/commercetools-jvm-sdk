package io.sphere.sdk.customers.messages;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.sphere.sdk.customers.Customer;
import io.sphere.sdk.messages.GenericMessageImpl;
import io.sphere.sdk.messages.MessageDerivateHint;
import io.sphere.sdk.messages.UserProvidedIdentifiers;

import java.time.LocalDate;
import java.time.ZonedDateTime;

/**
 * This message is the result of the {@link io.sphere.sdk.customers.commands.updateactions.SetDateOfBirth} update action.
 */
@JsonDeserialize(as = CustomerDateOfBirthSetMessage.class)//important to override annotation in Message class
public final class CustomerDateOfBirthSetMessage extends GenericMessageImpl<Customer> {
    public static final String MESSAGE_TYPE = "CustomerDateOfBirthSet";
    public static final MessageDerivateHint<CustomerDateOfBirthSetMessage> MESSAGE_HINT =
            MessageDerivateHint.ofSingleMessageType(MESSAGE_TYPE, CustomerDateOfBirthSetMessage.class, Customer.referenceTypeId());

    private final LocalDate dateOfBirth;

    @JsonCreator
    private CustomerDateOfBirthSetMessage(final String id, final Long version, final ZonedDateTime createdAt, final ZonedDateTime lastModifiedAt, final JsonNode resource, final Long sequenceNumber, final Long resourceVersion, final String type, final UserProvidedIdentifiers resourceUserProvidedIdentifiers, final LocalDate dateOfBirth) {
        super(id, version, createdAt, lastModifiedAt, resource, sequenceNumber, resourceVersion, type,resourceUserProvidedIdentifiers, Customer.class);
        this.dateOfBirth = dateOfBirth;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }
}
