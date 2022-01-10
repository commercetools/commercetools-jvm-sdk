package io.sphere.sdk.orders.messages;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.sphere.sdk.messages.GenericMessageImpl;
import io.sphere.sdk.messages.MessageDerivateHint;
import io.sphere.sdk.messages.UserProvidedIdentifiers;
import io.sphere.sdk.orders.Order;
import io.sphere.sdk.orders.ReturnInfo;

import javax.annotation.Nullable;
import java.time.ZonedDateTime;

@JsonDeserialize(as = ReturnInfoSetMessage.class)//important to override annotation in Message class
public final class ReturnInfoSetMessage extends GenericMessageImpl<Order> implements SimpleOrderMessage {
    public static final String MESSAGE_TYPE = "ReturnInfoSet";
    public static final MessageDerivateHint<ReturnInfoSetMessage> MESSAGE_HINT =
            MessageDerivateHint.ofSingleMessageType(MESSAGE_TYPE, ReturnInfoSetMessage.class, Order.referenceTypeId());

    private final ReturnInfo returnInfo;

    @JsonCreator
    private ReturnInfoSetMessage(final String id, final Long version, final ZonedDateTime createdAt, final ZonedDateTime lastModifiedAt, final JsonNode resource, final Long sequenceNumber, final Long resourceVersion, final String type, final UserProvidedIdentifiers resourceUserProvidedIdentifiers, @Nullable final ReturnInfo returnInfo) {
        super(id, version, createdAt, lastModifiedAt, resource, sequenceNumber, resourceVersion, type,resourceUserProvidedIdentifiers, Order.class);
        this.returnInfo = returnInfo;
    }

    @Nullable
    public ReturnInfo getReturnInfo() {
        return returnInfo;
    }
}
