package io.sphere.sdk.inventory.messages;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.sphere.sdk.channels.Channel;
import io.sphere.sdk.inventory.InventoryEntry;
import io.sphere.sdk.messages.GenericMessageImpl;
import io.sphere.sdk.messages.MessageDerivateHint;
import io.sphere.sdk.messages.UserProvidedIdentifiers;
import io.sphere.sdk.models.Reference;

import javax.annotation.Nullable;
import java.time.ZonedDateTime;

@JsonDeserialize(as = InventoryEntryCreatedMessage.class)//important to override annotation in Message class
public final class InventoryEntryCreatedMessage extends GenericMessageImpl<InventoryEntry> {
    public static final String MESSAGE_TYPE = "InventoryEntryCreated";
    public static final MessageDerivateHint<InventoryEntryCreatedMessage> MESSAGE_HINT =
            MessageDerivateHint.ofSingleMessageType(MESSAGE_TYPE, InventoryEntryCreatedMessage.class, InventoryEntry.referenceTypeId());

    private final String sku;
    @Nullable
    private final Reference<Channel> supplyChannel;

    @JsonCreator
    private InventoryEntryCreatedMessage(final String id, final Long version, final ZonedDateTime createdAt, final ZonedDateTime lastModifiedAt, final JsonNode resource, final Long sequenceNumber, final Long resourceVersion, final String type, final UserProvidedIdentifiers resourceUserProvidedIdentifiers, final String sku, final Reference<Channel> supplyChannel) {
        super(id, version, createdAt, lastModifiedAt, resource, sequenceNumber, resourceVersion, type,resourceUserProvidedIdentifiers, InventoryEntry.class);
        this.sku = sku;
        this.supplyChannel = supplyChannel;
    }

    public String getSku() {
        return sku;
    }

    @Nullable
    public Reference<Channel> getSupplyChannel() {
        return supplyChannel;
    }
}
