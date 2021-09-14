package io.sphere.sdk.stores.messages;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.sphere.sdk.messages.GenericMessageImpl;
import io.sphere.sdk.messages.MessageDerivateHint;
import io.sphere.sdk.messages.UserProvidedIdentifiers;
import io.sphere.sdk.stores.Store;

import java.time.ZonedDateTime;

@JsonDeserialize(as = StoreDeletedMessage.class)//important to override annotation in Message class
public final class StoreDeletedMessage extends GenericMessageImpl<Store> {
    public static final String MESSAGE_TYPE = "StoreDeleted";
    public static final MessageDerivateHint<StoreDeletedMessage> MESSAGE_HINT =
            MessageDerivateHint.ofSingleMessageType(MESSAGE_TYPE, StoreDeletedMessage.class, Store.referenceTypeId());

    @JsonCreator
    private StoreDeletedMessage(final String id, final Long version, final ZonedDateTime createdAt, final ZonedDateTime lastModifiedAt, final JsonNode resource, final Long sequenceNumber, final Long resourceVersion, final String type, final UserProvidedIdentifiers resourceUserProvidedIdentifiers) {
        super(id, version, createdAt, lastModifiedAt, resource, sequenceNumber, resourceVersion, type, resourceUserProvidedIdentifiers, Store.class);
    }
}
