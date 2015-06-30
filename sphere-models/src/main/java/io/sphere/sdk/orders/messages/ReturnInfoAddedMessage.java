package io.sphere.sdk.orders.messages;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.sphere.sdk.messages.GenericMessageImpl;
import io.sphere.sdk.messages.MessageDerivatHint;
import io.sphere.sdk.models.Reference;
import io.sphere.sdk.orders.ReturnInfo;
import io.sphere.sdk.orders.Order;
import io.sphere.sdk.queries.PagedQueryResult;

import java.time.ZonedDateTime;

@JsonDeserialize(as = ReturnInfoAddedMessage.class)//important to override annotation in Message class
public class ReturnInfoAddedMessage extends GenericMessageImpl<Order> {
    public static final MessageDerivatHint<ReturnInfoAddedMessage> MESSAGE_TYPE =
            MessageDerivatHint.of("ReturnInfoAdded",
                    new TypeReference<PagedQueryResult<ReturnInfoAddedMessage>>() { },
                    new TypeReference<ReturnInfoAddedMessage>() { }
            );

    private final ReturnInfo returnInfo;

    @JsonCreator
    private ReturnInfoAddedMessage(final String id, final long version, final ZonedDateTime createdAt, final ZonedDateTime lastModifiedAt, final Reference<Order> resource, final long sequenceNumber, final long resourceVersion, final String type, final ReturnInfo returnInfo) {
        super(id, version, createdAt, lastModifiedAt, resource, sequenceNumber, resourceVersion, type);
        this.returnInfo = returnInfo;
    }

    public ReturnInfo getReturnInfo() {
        return returnInfo;
    }
}
