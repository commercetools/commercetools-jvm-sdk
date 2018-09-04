package io.sphere.sdk.orders.messages;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.sphere.sdk.messages.GenericMessageImpl;
import io.sphere.sdk.messages.MessageDerivateHint;
import io.sphere.sdk.messages.UserProvidedIdentifiers;
import io.sphere.sdk.orders.Order;
import io.sphere.sdk.orders.ReturnInfo;

import java.time.ZonedDateTime;

@JsonDeserialize(as = ReturnInfoAddedMessage.class)//important to override annotation in Message class
public final class ReturnInfoAddedMessage extends GenericMessageImpl<Order> {
    public static final String MESSAGE_TYPE = "ReturnInfoAdded";
    public static final MessageDerivateHint<ReturnInfoAddedMessage> MESSAGE_HINT =
            MessageDerivateHint.ofSingleMessageType(MESSAGE_TYPE, ReturnInfoAddedMessage.class, Order.referenceTypeId());

    private final ReturnInfo returnInfo;

    @JsonCreator
    private ReturnInfoAddedMessage(final String id, final Long version, final ZonedDateTime createdAt, final ZonedDateTime lastModifiedAt, final JsonNode resource, final Long sequenceNumber, final Long resourceVersion, final String type, final UserProvidedIdentifiers resourceUserProvidedIdentifiers, final ReturnInfo returnInfo) {
        super(id, version, createdAt, lastModifiedAt, resource, sequenceNumber, resourceVersion, type,resourceUserProvidedIdentifiers, Order.class);
        this.returnInfo = returnInfo;
    }

    public ReturnInfo getReturnInfo() {
        return returnInfo;
    }
}
