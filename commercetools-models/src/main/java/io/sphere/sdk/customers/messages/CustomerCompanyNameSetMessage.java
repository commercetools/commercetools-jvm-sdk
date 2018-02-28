package io.sphere.sdk.customers.messages;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.sphere.sdk.customers.Customer;
import io.sphere.sdk.messages.GenericMessageImpl;
import io.sphere.sdk.messages.MessageDerivateHint;

import java.time.ZonedDateTime;

/**
 * This message is the result of the {@link io.sphere.sdk.customers.commands.updateactions.SetCompanyName} update action.
 */
@JsonDeserialize(as = CustomerCompanyNameSetMessage.class)//important to override annotation in Message class
public final class CustomerCompanyNameSetMessage extends GenericMessageImpl<Customer> {
    public static final String MESSAGE_TYPE = "CustomerCompanyNameSet";
    public static final MessageDerivateHint<CustomerCompanyNameSetMessage> MESSAGE_HINT =
            MessageDerivateHint.ofSingleMessageType(MESSAGE_TYPE, CustomerCompanyNameSetMessage.class, Customer.referenceTypeId());

    private final String companyName;

    @JsonCreator
    private CustomerCompanyNameSetMessage(final String id, final Long version, final ZonedDateTime createdAt, final ZonedDateTime lastModifiedAt, final JsonNode resource, final Long sequenceNumber, final Long resourceVersion, final String type, final String companyName) {
        super(id, version, createdAt, lastModifiedAt, resource, sequenceNumber, resourceVersion, type, Customer.class);
        this.companyName = companyName;
    }

    /**
     * The name of the company, which was added to the user.
     */
    public String getCompanyName() {
        return companyName;
    }
}
